package com.lhmd.rechnerarchitektur.events;

import java.util.*;
import java.util.function.Supplier;

public class ChangedEvent<T> {
    private final List<ChangeListener<T>> listeners;

    public ChangedEvent() {
        listeners = new ArrayList<>();
    }

    public List<ChangeListener<T>> getListeners() {
        return listeners;
    }

    public void addListener(ChangeListener<T> listener) {
        listeners.add(listener);
    }

    public void removeListener(ChangeListener<T> listener) {
        listeners.remove(listener);
    }

    public void fire(T oldValue, T newValue) {
        if (Objects.equals(oldValue, newValue)) {
            return;
        }

        for (var listener : listeners) {
            listener.changed(oldValue, newValue);
        }
    }

    public void fire(Supplier<T> getValue, Runnable setValue) {
        var oldValue = getValue.get();
        setValue.run();

        fire(oldValue, getValue.get());
    }
}
