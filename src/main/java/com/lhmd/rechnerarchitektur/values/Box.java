package com.lhmd.rechnerarchitektur.values;

import com.lhmd.rechnerarchitektur.changes.ChangeListener;

import java.util.*;

public class Box<T extends Comparable<T>> implements Comparable<Box<T>> {
    private final List<ChangeListener<T>> listeners;

    private T value;

    public Box(T value) {
        listeners = new ArrayList<>();
        setValue(value);
    }

    public Box() {
        this(null);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if (Objects.equals(this.value, value)) {
            return;
        }

        for (var listener : listeners) {
            listener.changed(this.value, value);
        }

        this.value = value;
    }

    public void addListener(ChangeListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(ChangeListener<T> listener) {
        listeners.remove(listener);
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
}
