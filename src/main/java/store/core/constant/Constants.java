package store.core.constant;

public class Constants {
    public static final String NEW_LINE = "\n";
    public static final String BLANK = "";
    public static final String SPACE = " ";
    public static final String HYPHEN = "- ";
    public static final String COMMA = ",";
    public static final String UNIT_ITEM = "개";
    public static final String WON = "원";
    public static final String NULL = "null";
    public static final String NO_STOCK = "재고 없음";
    public static final String CURRENT = "현재 ";
    public static final String SUBJECT_MARKER = "은(는) ";

    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;
    public static final int THIRD_ELEMENT = 2;
    public static final int FOURTH_ELEMENT = 3;
    public static final int FIFTH_ELEMENT = 4;

    public static final int PRODUCT_NAME_INDEX = 0;
    public static final int PRODUCT_QUANTITY_INDEX = 1;

    public static final String FILE_NAME_PRODUCTS = "products.md";
    public static final String FILE_NAME_PROMOTIONS = "promotions";

    public static final String WELCOME_MESSAGE = NEW_LINE + "안녕하세요. W편의점입니다." + NEW_LINE + "현재 보유하고 있는 상품입니다." + NEW_LINE + NEW_LINE;

    public static final String PURCHASE_PRODUCTS_QUANTITY = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String MEMBERSHIP_DISCOUNT_YES_NO = "멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String ADDITIONAL_PURCHASE_STATUS_YES_NO = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    public static final String FREE_ITEM_GUIDE_YES_NO = "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String PURCHASE_WITHOUT_DISCOUNT_YES_NO = "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";

    public static final String RECEIPT_STORE_NAME = "===========W 편의점=============\n";
    public static final String RECEIPT_HEAD = String.format("%-12s\t%-6s\t%-8s\n", "상품명","수량","금액");
    public static final String RECEIPT_FORMAT = "%-12s\t%-6d\t%-8s\n";
    public static final String RECEIPT_FORMAT_RESULT = "%-12s\t%-6s\t%-8s\n";
    public static final String RECEIPT_PRESENT = "===========증	정=============\n";
    public static final String RECEIPT_GUID_LINE = "==============================\n";
    public static final String TOTAL_PURCHASE_AMOUNT = "총구매액";
    public static final String PROMOTION_DISCOUNT = "행사할인";
    public static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    public static final String FINAL_PAYMENT = "내실돈";
}
