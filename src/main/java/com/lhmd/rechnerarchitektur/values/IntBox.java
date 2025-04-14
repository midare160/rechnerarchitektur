package com.lhmd.rechnerarchitektur.values;

public class IntBox extends Box<Integer> {
    public IntBox() {
        super(0);
    }

    public IntBox(int value) {
        super(value);
    }

    public int get() {
        return getValue();
    }

    public void set(int value) {
        setValue(value);
    }
}
