package com.lhmd.rechnerarchitektur.events;

import java.util.*;

public class ActionEvent {
    private final List<Runnable> listeners;

    public ActionEvent() {
        listeners = new ArrayList<>();
    }

    public List<Runnable> getListeners() {
        return listeners;
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public void removeListener(Runnable listener) {
        listeners.remove(listener);
    }

    public void fire() {
        for (var listener : listeners) {
            listener.run();
        }
    }
}
