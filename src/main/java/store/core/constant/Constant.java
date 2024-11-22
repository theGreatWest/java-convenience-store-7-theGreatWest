package store.core.constant;

public class Constant {
    public static final char OPENING_BRACKET = '[';
    public static final char CLOSING_BRACKET = ']';

    public static final String REGEX_ALPHA_NUMERIC = "[a-zA-Z가-힣0-9]+";
    public static final String REGEX_NUMERIC = "[0-9]+";

    public static final String HYPHEN = "-";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";
    public static final String WON = "원";
    public static final String GAE = "개";
    public static final String NO_STOCK = "재고 없음";
    public static final String NULL = "null";
    public static final String CURRENT = "현재";
    public static final String EUN_NEUN = "은(는)";

    public static final String WELCOME_MESSAGE = "\n안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n\n";
    public static final String INPUT_PRODUCTS_NAME_QUANTITY = "\n구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String INPUT_ASK_ADDITION_FREE_PRODUCT = "개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    public static final String INPUT_ASK_REGULAR_PAYMENT = "개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    public static final String INPUT_ASK_MEMBERSHIP = "\n멤버십 할인을 받으시겠습니까? (Y/N)";
    public static final String INPUT_ASK_CONTINUE = "\n감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public static final String RECEIPT = "\n===========W 편의점=============\n";
    public static final String RECEIPT_ = String.format("%-9s%9s%9s\n", "상품명","수량","금액");
    public static final String RECEIPT__ = "\n===========증    정=============\n";
    public static final String TOTAL_PRICE = "총구매액";
    public static final String PROMOTION_DISCOUNT = "행사할인";
    public static final String MEMBERSHIP_DISCOUNT = "멤버십할인";
    public static final String PAYMENT = "내실돈";
    public static final String RECEIPT___ = "\n===============================\n";
    public static final String FORMAT = "%-9s%9s%9s\n";
}
