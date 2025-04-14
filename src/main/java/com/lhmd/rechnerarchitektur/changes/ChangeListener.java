package com.lhmd.rechnerarchitektur.changes;

@FunctionalInterface
public interface ChangeListener<T> {
    void changed(T oldValue, T newValue);
}
