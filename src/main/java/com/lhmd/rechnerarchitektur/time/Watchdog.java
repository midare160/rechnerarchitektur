package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.events.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class Watchdog {
    public static final int RESET_TIME = 18_000;

    private final Prescaler prescaler;
    private final ApplicationEventPublisher eventPublisher;

    private int timer;

    public Watchdog(Prescaler prescaler, ApplicationEventPublisher eventPublisher) {
        this.prescaler = prescaler;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    @Order(EventOrders.EXECUTION)
    public void handleReset(ResetEvent event) {
        reset();
    }

    public void increment() {
        // Prescaler is assigned to Watchdog and did not overflow on increment => WDT does not get incremented
        if (prescaler.assignment() == PrescalerAssignment.WATCHDOG && !prescaler.increment()) {
            return;
        }

        timer++;

        if (timer >= RESET_TIME) {
            eventPublisher.publishEvent(new ResetEvent(this, ResetType.WATCHDOG));
        }
    }

    public void reset() {
        timer = 0;
    }
}
