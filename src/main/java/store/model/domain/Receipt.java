package store.model.domain;

public class Receipt {
    private StringBuilder receipt;
    private int totalPrice = 0;
    private int originalPrice = 0;
    private int promotionDiscount = 0;
    private int purchaseQuantity = 0;

    public Receipt(StringBuilder receipt) {
        this.receipt = receipt;
    }

    public void addTotalPrice(int totalPrice) {
        this.totalPrice += totalPrice;
    }

    public void addPromotionDiscount(int promotionDiscount) {
        this.promotionDiscount += promotionDiscount;
    }

    public void addPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity += purchaseQuantity;
    }

    public void addItem(String item){
        receipt.append(item);
    }
    public void addOriginalPrice(int value){
        this.originalPrice += value;
    }

    public StringBuilder getReceipt() {
        return receipt;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public String formatPriceWithComma(int price) {
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


}
