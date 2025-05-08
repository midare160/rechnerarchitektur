package com.lhmd.rechnerarchitektur.events;

import java.util.*;

public abstract class EventBase<TListener> {
    private final ArrayDeque<TListener> listeners;

    public EventBase() {
        listeners = new ArrayDeque<>();
    }

    public Queue<TListener> getListeners() {
        return listeners;
    }

    public void addListener(TListener listener) {
        listeners.addFirst(listener);
    }

    public void removeListener(TListener listener) {
        listeners.remove(listener);
    }
}
