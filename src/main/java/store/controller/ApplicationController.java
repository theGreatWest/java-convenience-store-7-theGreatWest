package store.controller;

import store.model.domain.PaymentResult;
import store.model.domain.Product;
import store.model.domain.UserRequest;
import store.model.dto.*;
import store.service.ApplicationService;
import store.view.InputView;
import store.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class ApplicationController {
    private final ApplicationService appService;
    private final InputView inputView;
    private final OutputView outputView;

    public ApplicationController(ApplicationService appService) {
        this.appService = appService;
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        while (true) {
            outputView.printProducts(appService.getProductsInventory());
            try {
                List<PaymentResult> finalPaymentResults = paymentResults(new UserRequestsDTO(appService.createUserRequestObject(promptProductsNameQuantity())));
                outputView.printReceipt(createReceipt(promptMembership(), finalPaymentResults));
                if (!promptInquireAnotherProducts()) { break; }
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public List<PaymentResult> paymentResults(UserRequestsDTO userRequestsDTO){
        UserRequestClassifiedPromotionDTO userRequestClassifiedBasedPromotionDTO = appService.userRequestClassifiedBasedPromotion(userRequestsDTO);
        List<PaymentResult> paymentResults = promptPromotionalPayment(userRequestClassifiedBasedPromotionDTO.getEligibleRequests());

        return nonPromotionProductsPayment(paymentResults, userRequestClassifiedBasedPromotionDTO.getIneligibleRequests());
    }

    public List<String[]> promptProductsNameQuantity() {
        while (true) {
            outputView.productNameQuantityPrompt();
            try {
                String userRequestProductsQuantity = appService.checkEmptyRequest(inputView.readProducts());

                return appService.parseUserRequestProductsNameQuantity(userRequestProductsQuantity);
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public List<PaymentResult> promptPromotionalPayment(List<UserRequest> userRequests) {
        List<PaymentResult> paymentResults = new ArrayList<>();
        for (UserRequest userRequest : userRequests) {
            ProductDetailsDTO productDetails = appService.productAllInfo(userRequest);
            if (userRequest.getQuantity() > productDetails.getQuantity()) {
                paymentResults.add(processInsufficientStock(userRequest));
                continue;
            }
            paymentResults.add(processSufficientStock(productDetails, userRequest));
        }
        return paymentResults;
    }


    public boolean promptConfirmNonDiscountProducts(PaymentResult paymentResult) {
        while (true) {
            outputView.printConfirmNonDiscountProductsPrompt(paymentResult.getUserRequestProductName(), paymentResult.getRemainQuantity());
            try {
                return appService.savedYesNoResult(inputView.readYesNo());
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public List<PaymentResult> nonPromotionProductsPayment(List<PaymentResult> paymentResults, List<UserRequest> userRequests){
        List<PaymentResult> finishPaymentResults = new ArrayList<>();
        for(PaymentResult paymentResult : paymentResults){
            finishPaymentResults.add(appService.nonPromotionProductsPayment(paymentResult));
        }
        for(UserRequest userRequest:userRequests){
            finishPaymentResults.add(appService.standardPayment(userRequest));
        }
        return finishPaymentResults;
    }


    public boolean promptConfirmAddProductPromotion(String productName, int additionalQuantity) {
        while (true) {
            outputView.printConfirmAddProductPromotionPrompt(productName, additionalQuantity);
            try {
                return appService.savedYesNoResult(inputView.readYesNo());
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }


    public String createReceipt(boolean membership, List<PaymentResult> paymentResults){
        int membershipDiscount = 0;
        if(membership){
            membershipDiscount = appService.countMembershipDiscount(paymentResults);
        }
        return appService.createReceipt(membershipDiscount, paymentResults);
    }



    public boolean promptMembership() {
        while (true) {
            outputView.membershipPrompt();
            try {
                return appService.savedYesNoResult(inputView.readYesNo());
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    public boolean promptInquireAnotherProducts() {
        while (true) {
            outputView.inquireAnotherProductsPrompt();
            try {
                return appService.savedYesNoResult(inputView.readYesNo());
            } catch (IllegalArgumentException e) {
                outputView.printExceptionMessage(e.getMessage());
            }
        }
    }

    private PaymentResult processInsufficientStock(UserRequest userRequest) {
        PaymentResult paymentResult = appService.overStockPurchase(userRequest);
        boolean answer = promptConfirmNonDiscountProducts(paymentResult);

        return appService.updateRemainingQuantity(answer, paymentResult);
    }

    private PaymentResult processSufficientStock(ProductDetailsDTO productDetails, UserRequest userRequest) {
        if (userRequest.getQuantity() % (productDetails.getPurchaseNumber() + productDetails.getGiftNumber()) == productDetails.getPurchaseNumber()) {
            if (promptConfirmAddProductPromotion(productDetails.getName(), productDetails.getGiftNumber())) {
                userRequest.changeQuantity(true, productDetails.getGiftNumber());
            }
        }

        return appService.underStockPurchase(userRequest);
    }
}
