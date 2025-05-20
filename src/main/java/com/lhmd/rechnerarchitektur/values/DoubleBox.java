package com.lhmd.rechnerarchitektur.values;

/**
 * Represents a {@code Box<Double>} that disallows {@code null} values.
 */
public class DoubleBox extends Box<Double> {
    public DoubleBox() {
        this(0d);
    }

    public DoubleBox(double value) {
        super(value, false);
    }

    public double get() {
        return getValue();
    }

    public void set(double value) {
        setValue(value);
    }
}
