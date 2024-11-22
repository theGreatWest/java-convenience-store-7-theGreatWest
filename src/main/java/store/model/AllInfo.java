package store.model;

import store.core.constant.Constant;

public class AllInfo {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    private int buy = -1;
    private int get = -1;
    private boolean active = false;

    public AllInfo(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void setPromotionDetails(Promotion promotionInfo) {
        if (!promotion.equalsIgnoreCase(Constant.NULL)) {
            this.buy = promotionInfo.getBuy();
            this.get = promotionInfo.getGet();
            this.active = promotionInfo.checkPromotionActive();
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public boolean isActive() {
        return active;
    }
}
