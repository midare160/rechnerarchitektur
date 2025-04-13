package com.lhmd.rechnerarchitektur.common;

import java.util.function.Consumer;

public class BooleanTempValue implements AutoCloseable {
    private final boolean valueSet;
    private final boolean initialValue;
    private final Consumer<Boolean> consumer;

    public BooleanTempValue(boolean initialValue, Consumer<Boolean> resetValue) {
        this.initialValue = initialValue;
        this.consumer = resetValue;
        this.valueSet = !initialValue;

        if (valueSet) {
            resetValue.accept(true);
        }
    }

    @Override
    public void close() {
        if (valueSet) {
            consumer.accept(initialValue);
        }
    }
}
