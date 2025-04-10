package com.lhmd.rechnerarchitektur.common;

@FunctionalInterface
public interface ChangeListener<T> {
    void changed(T oldValue, T newValue);
}
