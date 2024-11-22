package store.model;

import store.core.constant.Constant;

public class Receipt {
    private final Product product;
    private int totalQuantity = 0;
    private int freeQuantity = 0;
    private int totalPrice = 0;
    private int promotionDiscount = 0;

    public Receipt(Product product) {
        this.product = product;
    }

    public void addTotalQuantity(int quantity) {
        totalQuantity += quantity;
    }

    public void addFreeQuantity(int quantity) {
        freeQuantity += quantity;
    }

    public void addTotalPrice(int price) {
        totalPrice += price;
    }

    public void addPromotionDiscount(int price) {
        promotionDiscount += price;
    }

    public Product getProduct() {
        return product;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "product=" + product.getName() +
                ", totalQuantity=" + totalQuantity +
                ", freeQuantity=" + freeQuantity +
                ", totalPrice=" + totalPrice +
                ", promotionDiscount=" + promotionDiscount +
                '}';
    }
}
