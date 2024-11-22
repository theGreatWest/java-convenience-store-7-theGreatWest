package store.service;

import store.core.constant.Constant;
import store.core.constant.ExceptionMessage;
import store.model.AllInfo;
import store.model.Product;
import store.model.Receipt;
import store.model.UserRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppService {
    private final DatabaseService dbService;
    private final ValidationService validationService;

    public AppService(DatabaseService dbService, ValidationService validationService) {
        this.dbService = dbService;
        this.validationService = validationService;
    }

    public String stockStatus() {
        StringBuilder result = new StringBuilder();
        try {
            for (Product product : dbService.readAllProducts()) {
                result.append(product.getAnnounceMsg());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ETC.fullErrorMessage());
        }
        return result.toString();
    }

    public List<UserRequest> createUserRequestObj(String userInputProducts) {
        List<UserRequest> userRequests = new ArrayList<>();
        try {
            for (String requestProduct : userInputProducts.split(Constant.COMMA)) {
                String[] request = validationService.validateRequestProduct(requestProduct, dbService.readAllProductsName());
                userRequests.add(validationService.checkStock(dbService.totalQuantity(request[0]), request));
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return userRequests;
    }

    public boolean checkApplyPromotion(UserRequest userRequest) {
        return dbService.readTargetProduct(userRequest.getName(), true) != null;
    }

    public int checkLessRequest(UserRequest userRequest) {
        AllInfo allInfo = dbService.productAllInfo(userRequest.getName());
        if (allInfo.isActive() && userRequest.getQuantity() <= allInfo.getQuantity()) {
            int provideSet = allInfo.getBuy() + allInfo.getGet();
            if (userRequest.getQuantity() % provideSet == allInfo.getBuy()) return allInfo.getGet();
        }
        return 0;
    }

    public int checkLackStock(UserRequest userRequest) {
        AllInfo allInfo = dbService.productAllInfo(userRequest.getName());
        if (allInfo.isActive() && userRequest.getQuantity() > allInfo.getQuantity()) {
            int provideSet = allInfo.getBuy() + allInfo.getGet();
            return userRequest.getQuantity() - provideSet * (allInfo.getQuantity() / provideSet);
        }
        return 0;
    }

    public List<Receipt> payment(List<UserRequest> userRequests) {
        List<Receipt> receipts = new ArrayList<>();
        for (UserRequest userRequest : userRequests) {
            Product product = dbService.readTargetProduct(userRequest.getName(), false);
            AllInfo promotionInfo = dbService.productAllInfo(userRequest.getName());
            Receipt receipt = new Receipt(product);
            if (promotionInfo != null) paymentPromotionStock(receipt, promotionInfo, userRequest);
            paymentRegularStock(receipt, product, userRequest);
            receipts.add(receipt);
        }
        return receipts;
    }

    public String createFinalReceipt(List<Receipt> receipts, boolean membership){
        StringBuilder totalItem = new StringBuilder(), freeItem = new StringBuilder();
        int totalPrice = 0, totalQuantity=0, totalPromotionDiscount=0, totalRegularPrice=0;
        for(Receipt rp : receipts){
            totalItem.append(String.format(Constant.FORMAT, rp.getProduct().getName(), rp.getTotalQuantity(), priceWithComma(rp.getTotalPrice())));
            if(rp.getFreeQuantity()!=0) freeItem.append(String.format(Constant.FORMAT, rp.getProduct().getName(), rp.getFreeQuantity(), Constant.SPACE));
            totalPrice += rp.getTotalPrice();
            totalQuantity += rp.getTotalQuantity();
            totalPromotionDiscount += rp.getPromotionDiscount();
            totalRegularPrice += rp.getTotalPrice() - rp.getPromotionDiscount();
        }
        return createReceipt(totalRegularPrice, totalPrice, totalPromotionDiscount,totalQuantity,totalItem, freeItem, membership);
    }

    private String createReceipt(int totalRegularPrice, int totalPrice, int totalPromotionDiscount,int totalQuantity, StringBuilder totalItem, StringBuilder freeItem, boolean membership){
        StringBuilder receipt = new StringBuilder(Constant.RECEIPT + Constant.RECEIPT_);
        double membershipDiscount = 0.0;
        if(membership) membershipDiscount =Math.min(totalRegularPrice * 0.3, 8000) ;
        double finalPrice = totalPrice - totalPromotionDiscount - membershipDiscount;
        receipt.append(totalItem).append(Constant.RECEIPT__).append(freeItem).append(Constant.RECEIPT___)
                .append(String.format(Constant.FORMAT,Constant.TOTAL_PRICE, totalQuantity, priceWithComma(totalPrice)))
                .append(String.format(Constant.FORMAT,Constant.PROMOTION_DISCOUNT,Constant.SPACE,Constant.HYPHEN+priceWithComma(totalPromotionDiscount)))
                .append(String.format(Constant.FORMAT,Constant.MEMBERSHIP_DISCOUNT, Constant.SPACE, Constant.HYPHEN+priceWithComma((int)membershipDiscount)))
                .append(String.format(Constant.FORMAT,Constant.PAYMENT,Constant.SPACE, priceWithComma((int)finalPrice)));
        return receipt.toString();
    }

    private String priceWithComma(int price) {
        StringBuilder result = new StringBuilder();
        int cnt = 0;
        for (char c : new StringBuilder(Integer.toString(price)).reverse().toString().toCharArray()) {
            result.append(c);
            if (++cnt % 3 == 0) {
                cnt = 0;
                result.append(Constant.COMMA);
            }
        }
        String processedPrice = result.reverse().toString();
        if (processedPrice.charAt(0) == Constant.COMMA.charAt(0)) processedPrice = processedPrice.substring(1);
        return processedPrice;
    }

    private void paymentPromotionStock(Receipt receipt, AllInfo allInfo, UserRequest userRequest) {
        if (allInfo.isActive()) {
            int provideSet = allInfo.getBuy() + allInfo.getGet(), freeQuantity = userRequest.getQuantity() / provideSet * allInfo.getGet(), totalQuantity = provideSet * (allInfo.getQuantity() / provideSet);
            receipt.addFreeQuantity(freeQuantity);
            receipt.addPromotionDiscount(freeQuantity * allInfo.getPrice());
            receipt.addTotalQuantity(totalQuantity);
            receipt.addTotalPrice(totalQuantity * allInfo.getPrice());
            try {
                userRequest.updateQuantity(false, totalQuantity);
                dbService.updateProductQuantity(allInfo.getName(), false, totalQuantity);
            } catch (IOException e) {}
        }
    }

    private void paymentRegularStock(Receipt receipt, Product product, UserRequest userRequest) {
        receipt.addTotalQuantity(userRequest.getQuantity());
        receipt.addTotalPrice(userRequest.getQuantity() * product.getPrice());
        try {
            dbService.updateProductQuantity(userRequest.getName(), false, userRequest.getQuantity());
            userRequest.updateQuantity(false, userRequest.getQuantity());
        } catch (IOException e) {}
    }
}
