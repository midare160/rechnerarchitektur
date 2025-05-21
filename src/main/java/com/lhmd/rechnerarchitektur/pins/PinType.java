package com.lhmd.rechnerarchitektur.pins;

public enum PinType {
    A(5),
    B(8);

    private final int pinCount;

    PinType(int pinCount) {
        this.pinCount = pinCount;
    }

    public int pinCount() {
        return pinCount;
    }
}
