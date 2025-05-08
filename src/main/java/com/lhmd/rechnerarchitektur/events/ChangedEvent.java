package com.lhmd.rechnerarchitektur.events;

import java.util.*;
import java.util.function.Supplier;

public class ChangedEvent<T> extends EventBase<ChangeListener<T>> {
    public void fire(T oldValue, T newValue) {
        if (Objects.equals(oldValue, newValue)) {
            return;
        }

        for (var listener : getListeners()) {
            listener.changed(oldValue, newValue);
        }
    }

    public void fire(Supplier<T> getValue, Runnable setValue) {
        var oldValue = getValue.get();
        setValue.run();

        fire(oldValue, getValue.get());
    }
}
