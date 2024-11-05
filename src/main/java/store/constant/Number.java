package store.constant;

public enum Number {
    ZERO(0);

    private final int value;

    Number(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
