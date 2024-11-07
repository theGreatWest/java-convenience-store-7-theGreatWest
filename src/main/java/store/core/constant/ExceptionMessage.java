package store.core.constant;

public enum ExceptionMessage {
    ERROR("[ERROR]"),
    FILE_READER_IO_EXCEPTION(" 존재하지 않는 파일입니다. 파일명이 제대로 입력되었는지 확인해 주세요."),
    INVALID_TYPE_EXCEPTION(" 정수로 변환할 수 없는 값입니다. 물품의 수량과 가격을 확인해 주세요."),
    PROMOTION_EXPIRED(" 종료된 프로모션입니다."),
    FORMAT_EXCEPTION(" 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NON_EXISTENT_EXCEPTION(" 존재하지 않는 상품입니다. 다시 입력해 주세요."),
    EXCESS_QUANTITY_EXCEPTION(" 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    OTHER_EXCEPTION(" 잘못된 입력입니다. 다시 입력해 주세요.");

    private final String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String errorNotification(){
        return ERROR.getMessage() + getMessage();
    }

    public String getMessage() {
        return exceptionMessage;
    }
}
