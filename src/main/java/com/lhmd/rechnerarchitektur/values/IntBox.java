package com.lhmd.rechnerarchitektur.values;

import com.lhmd.rechnerarchitektur.common.IntUtils;

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

    public void increment() {
        set(get() + 1);
    }

    public void decrement() {
        set(get() - 1);
    }

    public boolean isBitSet(int index) {
        return IntUtils.isBitSet(get(), index);
    }

    public void setBit(int index, boolean value) {
        set(IntUtils.changeBit(get(), index, value));
    }
}
