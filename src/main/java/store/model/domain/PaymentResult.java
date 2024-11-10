package store.model.domain;

public class PaymentResult {
    private final String UserRequestProductName;
    private final int  productPrice;
    private int UserRequestQuantity;
    private int totalPrice=0;
    private int promotionDiscount=0;
    private int remainQuantity=0;
    private int giftNumber=0;
    private int nonPromotionNumber=0;

    public PaymentResult(String userRequestProductName,int productPrice, int UserRequestQuantity, int totalPrice, int promotionDiscount, int remainQuantity, int giftNumber, int nonPromotionNumber) {
        this.UserRequestProductName = userRequestProductName;
        this.productPrice = productPrice;
        this.UserRequestQuantity = UserRequestQuantity;
        this.totalPrice = totalPrice;
        this.promotionDiscount = promotionDiscount;
        this.remainQuantity = remainQuantity;
        this.giftNumber = giftNumber;
        this.nonPromotionNumber = nonPromotionNumber;
    }

    public String getUserRequestProductName() {
        return UserRequestProductName;
    }

    public int getNonPromotionNumber() {
        return nonPromotionNumber;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getUserRequestQuantity() {
        return UserRequestQuantity;
    }

    public int getGiftNumber() {
        return giftNumber;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public void addPayment(int price, int quantity){
        this.totalPrice += price;
        this.remainQuantity -=quantity;
    }

    public void reduceRequestQuantity(){
        this.UserRequestQuantity -= this.remainQuantity;
    }

    public void clearRemainQuantity(){
        this.remainQuantity = 0;
    }
}
