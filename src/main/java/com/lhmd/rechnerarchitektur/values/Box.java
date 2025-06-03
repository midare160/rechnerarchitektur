package com.lhmd.rechnerarchitektur.values;

import com.lhmd.rechnerarchitektur.events.ChangedEvent;

import java.io.*;
import java.util.*;

public class Box<T extends Comparable<T>> implements Comparable<Box<T>>, Serializable {
    @Serial
    private static final long serialVersionUID = 1;

    private final boolean nullable;
    private transient ChangedEvent<T> onChanged;

    private T value;

    public Box() {
        this(null);
    }

    public Box(T value) {
        this(value, true);
    }

    public Box(T value, boolean nullable) {
        this.value = value;
        this.nullable = nullable;
        this.onChanged = new ChangedEvent<>();

        throwWhenNotNullable(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        throwWhenNotNullable(value);
        onChanged.fire(() -> this.value, () -> this.value = value);
    }

    public ChangedEvent<T> onChanged() {
        return onChanged;
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Box<?> box)) {
            return false;
        }

        return Objects.equals(value, box.value);
    }

    @Override
    public int compareTo(Box<T> other) {
        if (other == null) return 1;
        if (value == other.value) return 0;
        if (value == null) return -1;
        if (other.value == null) return 1;

        return value.compareTo(other.value);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        onChanged = new ChangedEvent<>();
    }

    private void throwWhenNotNullable(T value) {
        if (value == null && !nullable) {
            throw new NullPointerException("Value cannot be null");
        }
    }
}
