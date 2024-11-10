package store.service;

import store.core.constant.Constants;
import store.model.domain.PaymentResult;
import store.model.domain.Product;
import store.model.domain.Receipt;
import store.model.domain.UserRequest;
import store.model.dto.*;

import java.util.*;

public class ApplicationService {
    private final DatabaseService dbService;
    private ValidationService validationService;

    public ApplicationService(DatabaseService dbService, ValidationService validationService) {
        this.dbService = dbService;
        this.validationService = validationService;
    }

    public String getProductsInventory() {
        List<Product> allProductsItems = dbService.readAllProducts();

        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : allProductsItems) {
            stringBuilder.append(product.toString());
        }

        return stringBuilder.toString();
    }

    public List<String[]> parseUserRequestProductsNameQuantity(String userRequestProductQuantity) {
        validationService = new ValidationService(dbService.readAllProducts(), dbService.readAllProductsName());
        List<String[]> userRequests = new ArrayList<>();
        for (String userRequestProduct : userRequestProductQuantity.split(Constants.COMMA)) {
            try {
                userRequests.add(validationService.startProductValidation(userRequestProduct));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return userRequests;
    }

    public List<UserRequest> createUserRequestObject(List<String[]> productsNameQuantity) {
        List<UserRequest> userRequests = new ArrayList<>();
        for (String[] userRequest : productsNameQuantity) {
            userRequests.add(new UserRequest(userRequest[Constants.PRODUCT_NAME_INDEX], Integer.parseInt(userRequest[Constants.PRODUCT_QUANTITY_INDEX])));
        }

        return userRequests;
    }

    public boolean savedYesNoResult(String answer) {
        try {
            return validationService.startYesNoValidation(answer);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String checkEmptyRequest(String inputValue) {
        try {
            return validationService.isEmpty(inputValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public UserRequestClassifiedPromotionDTO userRequestClassifiedBasedPromotion(UserRequestsDTO allUserRequests) {
        List<UserRequest> promotionEligibleRequests = new ArrayList<>();
        List<UserRequest> promotionIneligibleRequests = new ArrayList<>();
        for (UserRequest userRequest : allUserRequests.getUserRequests()) {
            if (classifyUserRequests(userRequest)) {
                promotionEligibleRequests.add(userRequest);
                continue;
            }
            promotionIneligibleRequests.add(userRequest);
        }
        return new UserRequestClassifiedPromotionDTO(promotionEligibleRequests, promotionIneligibleRequests);
    }

    public boolean classifyUserRequests(UserRequest userRequest) {
        boolean result = false;
        for (Product product : dbService.searchProducts(userRequest.getProductName())) {
            if (!product.getPromotion().equalsIgnoreCase(Constants.NULL) && (dbService.searchPromotion(product.getPromotion())!=null)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public ProductDetailsDTO productAllInfo(UserRequest userRequest) {
        return dbService.searchProductDetails(userRequest.getProductName());
    }


    public PaymentResult overStockPurchase(UserRequest userRequest) {
        ProductDetailsDTO productInfo = productAllInfo(userRequest);
        int totalBuyGet = productInfo.getPurchaseNumber() + productInfo.getGiftNumber();
        int applicableBundle = productInfo.getQuantity() / totalBuyGet;
        int totalPrice = applicableBundle * productInfo.getPurchaseNumber() * productInfo.getPrice();
        int promotionalQuantity = applicableBundle * totalBuyGet;
        int remainQuantity = userRequest.getQuantity() - promotionalQuantity;
        int promotionDiscount = promotionalQuantity * productInfo.getPrice() - totalPrice;
        dbService.updateStock(userRequest.getProductName(), true, false, promotionalQuantity);

        return new PaymentResult(productInfo.getName(), productInfo.getPrice(), userRequest.getQuantity(), totalPrice, promotionDiscount, remainQuantity, applicableBundle * productInfo.getGiftNumber(), remainQuantity);
    }


    public PaymentResult underStockPurchase(UserRequest userRequest) {
        ProductDetailsDTO productInfo = productAllInfo(userRequest);
        int totalBuyGet = productInfo.getPurchaseNumber() + productInfo.getGiftNumber();
        int applicableBundle = userRequest.getQuantity() / totalBuyGet;
        int totalPrice = (userRequest.getQuantity() - (applicableBundle * productInfo.getGiftNumber())) * productInfo.getPrice();
        int promotionDiscount = applicableBundle * productInfo.getGiftNumber() * productInfo.getPrice();

        dbService.updateStock(userRequest.getProductName(), true, false, userRequest.getQuantity());

        return new PaymentResult(productInfo.getName(), productInfo.getPrice(), userRequest.getQuantity(), totalPrice, promotionDiscount, 0, applicableBundle * productInfo.getGiftNumber(), 0);
    }


    public PaymentResult updateRemainingQuantity(boolean answer, PaymentResult paymentResult) {
        ProductDetailsDTO productAllInfo = dbService.searchProductDetails(paymentResult.getUserRequestProductName());
        if (!answer) {
            paymentResult.reduceRequestQuantity();
            paymentResult.clearRemainQuantity();
            return paymentResult;
        }
        paymentResult.addPayment(productAllInfo.getQuantity() * productAllInfo.getPrice(), productAllInfo.getQuantity());
        dbService.updateStock(productAllInfo.getName(), true, false, productAllInfo.getQuantity());
        return paymentResult;
    }

    public PaymentResult nonPromotionProductsPayment(PaymentResult paymentResult) {
        Product product = dbService.searchProductNonPromotion(paymentResult.getUserRequestProductName());
        dbService.updateStock(paymentResult.getUserRequestProductName(), false, false, paymentResult.getRemainQuantity());
        paymentResult.addPayment(paymentResult.getRemainQuantity() * product.getPrice(), paymentResult.getRemainQuantity());

        return paymentResult;
    }

    public PaymentResult standardPayment(UserRequest userRequest) {
        Product product = dbService.searchProductNonPromotion(userRequest.getProductName());

        int price = userRequest.getQuantity() * product.getPrice();
        dbService.updateStock(userRequest.getProductName(), false, false, userRequest.getQuantity());

        return new PaymentResult(userRequest.getProductName(), product.getPrice(), userRequest.getQuantity(), price, 0, 0, 0, userRequest.getQuantity());
    }

    public int countMembershipDiscount(List<PaymentResult> paymentResults) {
        int nonPromotionAmount = 0;
        for (PaymentResult paymentResult : paymentResults) {
            nonPromotionAmount += paymentResult.getNonPromotionNumber() * paymentResult.getProductPrice();
        }
        return Math.min((nonPromotionAmount / 10 * 3), 8000);
    }

    public String createReceipt(int membershipDiscount, List<PaymentResult> paymentResults) {
        Receipt receipt = new Receipt(new StringBuilder(Constants.RECEIPT_STORE_NAME).append(Constants.RECEIPT_HEAD));
        for (PaymentResult paymentResult : paymentResults) {
            receipt.addItem(String.format(Constants.RECEIPT_FORMAT, paymentResult.getUserRequestProductName(), paymentResult.getUserRequestQuantity(), receipt.formatPriceWithComma(paymentResult.getUserRequestQuantity() * paymentResult.getProductPrice())));
            receipt.addTotalPrice(paymentResult.getTotalPrice());
            receipt.addOriginalPrice(paymentResult.getUserRequestQuantity()*paymentResult.getProductPrice());
            receipt.addPromotionDiscount(paymentResult.getPromotionDiscount());
            receipt.addPurchaseQuantity(paymentResult.getUserRequestQuantity());
        }
        getGiftDetails(receipt, paymentResults, membershipDiscount);
        return receipt.getReceipt().toString();
    }

    private void getGiftDetails(Receipt receipt, List<PaymentResult> paymentResults, int membershipDiscount) {
        receipt.addItem(Constants.RECEIPT_PRESENT);
        for (PaymentResult paymentResult : paymentResults) {
            if (paymentResult.getGiftNumber() != 0) {
                receipt.addItem(String.format(Constants.RECEIPT_FORMAT_RESULT, paymentResult.getUserRequestProductName(), paymentResult.getGiftNumber(), Constants.BLANK));
            }
        }
        getFinalReceipt(receipt, membershipDiscount);
    }

    private void getFinalReceipt(Receipt receipt, int membershipDiscount) {
        receipt.addItem(Constants.RECEIPT_GUID_LINE);
        receipt.addItem(String.format(Constants.RECEIPT_FORMAT_RESULT, Constants.TOTAL_PURCHASE_AMOUNT, receipt.getPurchaseQuantity(), receipt.getOriginalPrice()));
        receipt.addItem(String.format(Constants.RECEIPT_FORMAT_RESULT, Constants.PROMOTION_DISCOUNT , Constants.BLANK, Constants.HYPHEN.strip() + receipt.formatPriceWithComma(receipt.getPromotionDiscount())));
        receipt.addItem(String.format(Constants.RECEIPT_FORMAT_RESULT, Constants.MEMBERSHIP_DISCOUNT   ,Constants.BLANK, Constants.HYPHEN.strip() + receipt.formatPriceWithComma(membershipDiscount)));
        receipt.addItem(String.format(Constants.RECEIPT_FORMAT_RESULT, Constants.FINAL_PAYMENT, Constants.BLANK, receipt.formatPriceWithComma(receipt.getTotalPrice()-membershipDiscount)));
    }


}
