package com.lhmd.rechnerarchitektur.common;

import java.util.*;

public class Box<T> {
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
}
