package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.time.RuntimeManager;
import com.lhmd.rechnerarchitektur.time.Watchdog;
import com.lhmd.rechnerarchitektur.values.DoubleBox;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SleepManager implements AutoCloseable {
    private final UserConfig userConfig;
    private final DoubleBox runtime;
    private final InterruptManager interruptManager;
    private final Watchdog watchdog;
    private final ApplicationEventPublisher eventPublisher;

    private boolean isSleeping;

    public SleepManager(
            UserConfigService userConfigService,
            RuntimeManager runtimeManager,
            InterruptManager interruptManager,
            Watchdog watchdog,
            ApplicationEventPublisher eventPublisher) {
        this.userConfig = userConfigService.config();
        this.runtime = runtimeManager.runtime();
        this.interruptManager = interruptManager;
        this.watchdog = watchdog;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void close() {
        wakeup();
    }

    @EventListener
    @Order(EventOrders.EXECUTION)
    public void handleReset(ResetEvent event) {
        wakeup();
    }

    public void sleep() {
        isSleeping = true;

        while (isSleeping) {
            var counter = 0L;

            // HACK: Simulate sleeping less than a millisecond
            while (counter < 10_000L * userConfig.getExecutionInterval()) {
                counter++;
            }

            runtime.set(runtime.get() + 1);

            if (userConfig.isWatchdogEnabled()) {
                watchdog.increment();
            }

            if (interruptManager.isInterrupted()) {
                eventPublisher.publishEvent(new ResetEvent(this, ResetType.WAKEUP_INTERRUPT));
                interruptManager.handleInterrupt();
            }
        }
    }

    public void wakeup() {
        isSleeping = false;
    }
}
