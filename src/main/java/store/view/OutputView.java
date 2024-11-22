package store.view;

import store.core.constant.Constant;

public class OutputView {
    public void printWelcomeStockStatus(String stockStatus) {
        System.out.println(Constant.WELCOME_MESSAGE + stockStatus + Constant.INPUT_PRODUCTS_NAME_QUANTITY);
    }

    public void printAskAdditionFreeProduct(String productName, int get) {
        System.out.println(Constant.CURRENT + Constant.SPACE + productName + Constant.EUN_NEUN + Constant.SPACE + get + Constant.INPUT_ASK_ADDITION_FREE_PRODUCT);
    }

    public void printAskRegularPayment(String productName, int regularQuantity) {
        System.out.println(Constant.CURRENT + Constant.SPACE + productName + Constant.SPACE + regularQuantity + Constant.INPUT_ASK_REGULAR_PAYMENT);
    }

    public void printAskMembership() {
        System.out.println(Constant.INPUT_ASK_MEMBERSHIP);
    }

    public void printReceipt(String receipt) {
        System.out.println(receipt);
    }

    public void printAskContinue() {
        System.out.println(Constant.INPUT_ASK_CONTINUE);
    }

    public void printExceptionMsg(String errorMsg) {
        System.out.println(errorMsg);
    }

}
