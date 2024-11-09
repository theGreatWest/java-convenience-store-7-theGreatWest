package store.model.dto;

public class userRequest {
    private final String productName;
    private final int productQuantity;

    public userRequest(String productName, int productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
