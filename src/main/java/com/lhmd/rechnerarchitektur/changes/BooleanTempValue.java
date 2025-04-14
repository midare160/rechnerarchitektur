package com.lhmd.rechnerarchitektur.changes;

import java.util.function.Consumer;

public class BooleanTempValue implements AutoCloseable {
    private final boolean valueSet;
    private final boolean initialValue;
    private final Consumer<Boolean> resetValue;

    public BooleanTempValue(boolean initialValue, boolean temporaryValue, Consumer<Boolean> resetValue) {
        this.initialValue = initialValue;
        this.resetValue = resetValue;
        this.valueSet = initialValue != temporaryValue;

        if (valueSet) {
            resetValue.accept(temporaryValue);
        }
    }

    @Override
    public void close() {
        if (valueSet) {
            resetValue.accept(initialValue);
        }
    }
}
