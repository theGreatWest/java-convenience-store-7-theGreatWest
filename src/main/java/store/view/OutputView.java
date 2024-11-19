package store.view;

import store.core.constant.Constant;

public class OutputView {
    public void printWelcomeStockStatus(String stockStatus){
        System.out.println(Constant.WELCOME_MESSAGE + stockStatus + Constant.INPUT_PRODUCTS_NAME_QUANTITY);
    }

    public void printExceptionMsg(String errorMsg){
        System.out.println(errorMsg);
    }

}
