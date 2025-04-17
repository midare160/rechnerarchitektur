package com.lhmd.rechnerarchitektur.values;

import java.util.Objects;

/**
 * Represents a {@code Box<Integer>} that disallows null values.
 */
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

    @Override
    public void setValue(Integer value) {
        Objects.requireNonNull(value);
        super.setValue(value);
    }
}
