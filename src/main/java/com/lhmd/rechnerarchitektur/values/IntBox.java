package com.lhmd.rechnerarchitektur.values;

/**
 * Represents a {@code Box<Integer>} that disallows {@code null} values.
 */
public class IntBox extends Box<Integer> {
    public IntBox() {
        this(0);
    }

    public IntBox(int value) {
        super(value, false);
    }

    public int get() {
        return getValue();
    }

    public void set(int value) {
        setValue(value);
    }
}
