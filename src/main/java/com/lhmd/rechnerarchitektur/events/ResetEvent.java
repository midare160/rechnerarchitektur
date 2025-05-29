package com.lhmd.rechnerarchitektur.events;

import org.springframework.context.ApplicationEvent;

public class ResetEvent extends ApplicationEvent {
    private final ResetType resetType;

    public ResetEvent(Object source, ResetType resetType) {
        super(source);
        this.resetType = resetType;
    }

    public ResetType resetType() {
        return resetType;
    }
}
