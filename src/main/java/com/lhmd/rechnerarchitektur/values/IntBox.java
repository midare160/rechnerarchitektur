package com.lhmd.rechnerarchitektur.values;

import java.util.Objects;

/**
 * Represents a {@code Box<Integer>} that disallows {@code null} values.
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
        super.setValue(Objects.requireNonNull(value));
    }
}
