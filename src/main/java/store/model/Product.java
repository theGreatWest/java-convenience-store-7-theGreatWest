package store.model;

import store.core.constant.Constant;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void updateStock(boolean operator, int changedQuantity) {
        if (operator) {
            quantity += changedQuantity;
            return;
        }
        quantity -= changedQuantity;
    }

    public String getFileLine() {
        return name + Constant.COMMA + price + Constant.COMMA + quantity + Constant.COMMA + promotion;
    }

    public String getAnnounceMsg() {
        String processedQuantity = quantity + Constant.GAE;
        if (quantity == 0) processedQuantity = Constant.NO_STOCK;

        String processdPromotion = promotion;
        if (promotion.equals(Constant.NULL)) processdPromotion = "";

        String processedPrice = priceWithComma();
        if (processedPrice.charAt(0) == Constant.COMMA.charAt(0)) processedPrice = processedPrice.substring(1);

        return Constant.HYPHEN + Constant.SPACE + name + Constant.SPACE + processedPrice + Constant.WON + Constant.SPACE + processedQuantity + Constant.SPACE + processdPromotion + Constant.NEW_LINE;
    }

    public String priceWithComma() {
        StringBuilder result = new StringBuilder();
        int cnt = 0;
        for (char c : new StringBuilder(Integer.toString(price)).reverse().toString().toCharArray()) {
            result.append(c);
            if (++cnt % 3 == 0) {
                cnt = 0;
                result.append(Constant.COMMA);
            }
        }
        return result.reverse().toString();
    }
}
