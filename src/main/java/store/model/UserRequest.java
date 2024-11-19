package store.model;

public class UserRequest {
    private final String name;
    private int quantity;

    public UserRequest(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public void updateQuantity(boolean operator, int changedQuantity) {
        if (operator) {
            quantity += changedQuantity;
            return;
        }
        quantity -= changedQuantity;
    }

    @Override
    public String toString() {
        return "name: " + name + " | quantity: " + quantity;
    }
}
