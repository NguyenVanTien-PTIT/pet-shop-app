package com.example.ecommerce_mobile_app.util;

public enum OrderStatus {
    ORDERING(0), WAIT_CONFIRM(1), DELIVERING(2), DELIVERED(3);

    OrderStatus(int value) {
        this.value = value;
    }

    private final int value;

    public int getValue() {
        return value;
    }
}
