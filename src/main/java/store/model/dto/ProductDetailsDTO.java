package store.model.dto;

public class ProductDetailsDTO {
    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;
    private final int purchaseNumber;
    private final int giftNumber;

    public ProductDetailsDTO(String name, int price, int quantity, String promotion, int purchaseNumber, int giftNumber) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.purchaseNumber = purchaseNumber;
        this.giftNumber = giftNumber;
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

    public int getPurchaseNumber() {
        return purchaseNumber;
    }

    public int getGiftNumber() {
        return giftNumber;
    }
}
