package store.model;

import store.core.constant.Constant;

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

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return name + Constant.COMMA + Constant.SPACE + quantity;
    }
}
