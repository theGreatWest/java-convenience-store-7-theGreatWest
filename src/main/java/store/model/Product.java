package store.model;

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
        final String DELIMITER = ",";

        return name + DELIMITER + price + DELIMITER + quantity + DELIMITER + promotion;
    }
}
