package com.lhmd.rechnerarchitektur.events;

/**
 * @see org.springframework.core.annotation.Order
 */
public final class EventOrders {
    private EventOrders() {
    }

    public static final int DATA = 0;
    public static final int EXECUTION = 1;
    public static final int UI = 2;
}
