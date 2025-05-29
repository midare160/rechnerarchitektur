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
    private final Consumer<T> setValue;
    private final boolean isValueSet;

    public TempValue(T initialValue, T temporaryValue, Consumer<T> setValue) {
        this.initialValue = initialValue;
        this.setValue = setValue;
        this.isValueSet = !Objects.equals(initialValue, temporaryValue);

        if (isValueSet) {
            setValue.accept(temporaryValue);
        }
    }

    @Override
    public void close() {
        if (isValueSet) {
            setValue.accept(initialValue);
        }
    }
}
