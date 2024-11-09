package store.model.domain;

import store.core.util.Date;

public class Promotion {
    public static final String NEW_LINE = "\n";
    public static final String SLASH = " / ";

    private String name;
    private int purchaseNumber;
    private int giftNumber;
    private String startDate;
    private String endDate;

    public Promotion(String name, String purchaseNumber, String giftNumber, String startDate, String endDate) {
        if (validateExpiration(startDate, endDate) && validateStringToInteger(purchaseNumber, giftNumber)) {
            this.name = name;
            this.purchaseNumber = Integer.parseInt(purchaseNumber);
            this.giftNumber = Integer.parseInt(giftNumber);
            this.startDate = startDate;
            this.endDate = endDate;

            return;
        }
        throw new IllegalStateException();
    }

    private boolean validateExpiration(String startDate, String endDate) {
        return Date.isDateInRange(startDate, endDate);
    }

    private boolean validateStringToInteger(String purchaseNumber, String giftNumber) {
        try {
            Integer.parseInt(purchaseNumber);
            Integer.parseInt(giftNumber);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return name + SLASH + purchaseNumber + SLASH + giftNumber + SLASH + startDate + SLASH + endDate + NEW_LINE;
    }
}
