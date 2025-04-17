package com.lhmd.rechnerarchitektur.changes;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents a helper class that stores an original value,
 * sets it to a temporary value and restores the original value on close.
 * If the original value was already the same as the temporary value, the original value stays unchanged.
 */
public class TempValue<T> implements AutoCloseable {
    private final T initialValue;
    private final Consumer<T> resetValue;
    private final boolean isValueSet;

    public TempValue(T initialValue, T temporaryValue, Consumer<T> resetValue) {
        this.initialValue = initialValue;
        this.resetValue = resetValue;
        this.isValueSet = !Objects.equals(initialValue, temporaryValue);

        if (isValueSet) {
            resetValue.accept(temporaryValue);
        }
    }

    @Override
    public void close() {
        if (isValueSet) {
            resetValue.accept(initialValue);
        }
    }
}
