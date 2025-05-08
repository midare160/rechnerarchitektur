package com.lhmd.rechnerarchitektur.events;

public class ActionEvent extends EventBase<Runnable> {
    public void fire() {
        for (var listener : getListeners()) {
            listener.run();
        }
    }
}
