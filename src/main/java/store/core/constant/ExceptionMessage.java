package store.core.constant;

public enum ExceptionMessage {
    ERROR("[ERROR] "),
    INVALID_PRODUCT_NANE_QUANTITY_FORMAT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXISTENT_PRODUCT("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCEED_STOCK_QUANTITY("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    INVALID_ETC("잘못된 입력입니다. 다시 입력해 주세요.");

    private final String errorMessage;

    ExceptionMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String errorMessage() {
        return errorMessage;
    }

    public String fullErrorMessage(){
        return ERROR.errorMessage() + errorMessage();
    }
}
