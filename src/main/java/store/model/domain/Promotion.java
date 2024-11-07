package store.model.domain;

import store.core.constant.ExceptionMessage;
import store.core.util.Date;

public class Promotion {
    private String name;
    private int purchaseNumber;
    private int giftNumber;
    private String startDate;
    private String endDate;

    public Promotion(String name, int purchaseNumber, int giftNumber, String startDate, String endDate) {
        if(validateExpiration(startDate, endDate)){
            this.name = name;
            this.purchaseNumber = purchaseNumber;
            this.giftNumber = giftNumber;
            this.startDate = startDate;
            this.endDate = endDate;

            return;
        }
        throw new IllegalStateException(ExceptionMessage.PROMOTION_EXPIRED.errorNotification());
    }

    private boolean validateExpiration(String startDate, String endDate){
        return Date.isDateInRange(startDate, endDate);
    }
}
