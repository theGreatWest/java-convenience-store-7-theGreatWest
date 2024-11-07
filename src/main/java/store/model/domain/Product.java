package store.model.domain;

public class Product {
    public static final String NEW_LINE = "\n";
    public static final String SLASH = " / ";

    private String name;
    private int price;
    private int quantity;
    private String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        if(validateStringToInteger(price, quantity)){
            this.name = name;
            this.price = Integer.parseInt(price);
            this.quantity = Integer.parseInt(quantity);
            this.promotion = promotion;
            return;
        }
        throw new IllegalStateException(name);
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

    @Override
    public String toString() {
        return name + SLASH + price + SLASH + quantity + SLASH + promotion + NEW_LINE;
    }
}
