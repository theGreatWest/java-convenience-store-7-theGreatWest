package store.core.constant;

public enum ExceptionMessage {
    ERROR("[ERROR]"),
    FILE_READER_IO_EXCEPTION(" 존재하지 않는 파일입니다. 파일이 제대로 입력되었는지 확인해 주세요."),
    UNSAVED_ITEM_IN_FILE(" 저장되지 않은 항목이 있어요. 상품명: "),
    FORMAT_EXCEPTION(" 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXISTENT_EXCEPTION(" 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCESS_QUANTITY_EXCEPTION(" 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    OTHER_INPUT_EXCEPTION(" 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String errorNotification() {
        return ERROR.getMessage() + getMessage();
    }

    public String getMessage() {
        return exceptionMessage;
    }
}
