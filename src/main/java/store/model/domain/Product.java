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

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    @Override
    public String toString() {
        return Constants.HYPHEN + name + store.core.constant.Constants.SPACE
                + formatPriceWithComma() + store.core.constant.Constants.WON + store.core.constant.Constants.SPACE
                + checkStockMessage() + store.core.constant.Constants.SPACE
                + checkPromotionMessage() + store.core.constant.Constants.NEW_LINE;
    }

    private String formatPriceWithComma() {
        String reversedPrice = new StringBuilder(Integer.toString(price)).reverse().toString();
        StringBuilder processedPrice = new StringBuilder();
        for (int i = 1; i <= reversedPrice.length(); i++) {
            processedPrice.append(reversedPrice.charAt(i - 1));
            if (i != reversedPrice.length() && i % 3 == 0) {
                processedPrice.append(store.core.constant.Constants.COMMA);
            }
        }

        return processedPrice.reverse().toString();
    }

    private String checkStockMessage() {
        if (quantity==0) {
            return store.core.constant.Constants.NO_STOCK;
        }
        return quantity + store.core.constant.Constants.UNIT_ITEM;
    }

    private String checkPromotionMessage() {
        if (quantity == 0 || promotion.equalsIgnoreCase(store.core.constant.Constants.NULL)) {
            return store.core.constant.Constants.SPACE;
        }
        return promotion;
    }
}
