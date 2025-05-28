package com.lhmd.rechnerarchitektur.values;

/**
 * Represents a {@code Box<Boolean>} that disallows {@code null} values.
 */
public class BoolBox extends Box<Boolean> {
    public BoolBox() {
        this(false);
    }

    public BoolBox(boolean value) {
        super(value, false);
    }

    public boolean get() {
        return getValue();
    }

    public void set(boolean value) {
        setValue(value);
    }

    public void toggle() {
        set(!get());
    }
}
