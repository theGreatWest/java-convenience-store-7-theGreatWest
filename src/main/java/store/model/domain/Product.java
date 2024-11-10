package store.model.domain;

import store.core.constant.Constants;

import java.util.List;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        if (validateStringToInteger(price, quantity)) {
            this.name = name;
            this.price = Integer.parseInt(price);
            this.quantity = Integer.parseInt(quantity);
            this.promotion = promotion;
            return;
        }
        throw new IllegalStateException();
    }

    public void changeQuantity(boolean request, int quantity) {
        if (request) {
            this.quantity += quantity;
            return;
        }
        this.quantity -= quantity;
    }

    private boolean validateStringToInteger(String price, String quantity) {
        try {
            Integer.parseInt(price);
            Integer.parseInt(quantity);

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

    public String getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return Constants.HYPHEN + name + Constants.SPACE
                + formatPriceWithComma() + Constants.WON + Constants.SPACE
                + checkStockMessage() + Constants.SPACE
                + checkPromotionMessage() + Constants.NEW_LINE;
    }

    private String formatPriceWithComma() {
        String reversedPrice = new StringBuilder(Integer.toString(price)).reverse().toString();
        StringBuilder processedPrice = new StringBuilder();
        for (int i = 1; i <= reversedPrice.length(); i++) {
            processedPrice.append(reversedPrice.charAt(i - 1));
            if (i != reversedPrice.length() && i % 3 == 0) {
                processedPrice.append(Constants.COMMA);
            }
        }

        return processedPrice.reverse().toString();
    }

    private String checkStockMessage() {
        if (quantity==0) {
            return Constants.NO_STOCK;
        }
        return quantity + Constants.UNIT_ITEM;
    }

    private String checkPromotionMessage() {
        if (quantity == 0 || promotion.equalsIgnoreCase(Constants.NULL)) {
            return Constants.SPACE;
        }
        return promotion;
    }
}
