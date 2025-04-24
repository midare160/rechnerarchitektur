package com.lhmd.rechnerarchitektur.events;

@FunctionalInterface
public interface ChangeListener<T> {
    void changed(T oldValue, T newValue);
}
