package com.lhmd.rechnerarchitektur.events;

import org.springframework.context.ApplicationEvent;

public class ResetEvent extends ApplicationEvent {
    public ResetEvent(Object source) {
        super(source);
    }
}
