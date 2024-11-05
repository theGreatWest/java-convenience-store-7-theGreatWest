package store.constant;

public enum ExceptionMessage {
    ERROR("[ERROR]"),
    FILE_READER_IO_EXCEPTION("[ERROR] 존재하지 않는 파일입니다. 파일명이 제대로 입력되었는지 확인해 주세요."),
    FORMAT_EXCEPTION("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXISTENT_EXCEPTION("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCESS_QUANTITY_EXCEPTION("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    OTHER_EXCEPTION("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage(){
        return exceptionMessage;
    }
}
