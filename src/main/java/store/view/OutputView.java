package store.view;

import store.core.constant.Constants;

public class OutputView {
    public void printProducts(String allProducts) {
        System.out.print(
                Constants.WELCOME_MESSAGE + allProducts
        );
    }

    public void productNameQuantityPrompt() {
        System.out.println(Constants.NEW_LINE + Constants.PURCHASE_PRODUCTS_QUANTITY);
    }

    public void membershipPrompt() {
        System.out.println(Constants.NEW_LINE + Constants.MEMBERSHIP_DISCOUNT_YES_NO);
    }

    public void inquireAnotherProductsPrompt(){
        System.out.println(Constants.NEW_LINE + Constants.ADDITIONAL_PURCHASE_STATUS_YES_NO);
    }


    public void printExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

    public void printConfirmNonDiscountProductsPrompt(String productName, int insufficientQuantity){
        System.out.println(Constants.NEW_LINE + Constants.CURRENT + productName+Constants.SPACE+insufficientQuantity+Constants.PURCHASE_WITHOUT_DISCOUNT_YES_NO);
    }

    public void printConfirmAddProductPromotionPrompt(String productName, int additionalQuantity){
        System.out.println(Constants.NEW_LINE + Constants.CURRENT + productName+Constants.SUBJECT_MARKER+additionalQuantity+Constants.FREE_ITEM_GUIDE_YES_NO);
    }

    public void printReceipt(String receipt) {
        System.out.println(Constants.NEW_LINE + receipt);
    }
}
