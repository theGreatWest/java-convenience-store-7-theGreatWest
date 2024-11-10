package store.model.domain;

public class UserRequest {
    private final String productName;
    private int quantity;

    public UserRequest(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public void changeQuantity(boolean request, int quantity) {
        if (request) {
            this.quantity += quantity;
            return;
        }
        this.quantity -= quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
