package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.time.Watchdog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class SleepManager implements AutoCloseable {
    private final UserConfig userConfig;
    private final InterruptManager interruptManager;
    private final Watchdog watchdog;
    private final ApplicationEventPublisher eventPublisher;

    private boolean isSleeping;

    public SleepManager(
            UserConfigService userConfigService,
            InterruptManager interruptManager,
            Watchdog watchdog,
            ApplicationEventPublisher eventPublisher) {
        this.userConfig = userConfigService.config();
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
            Runner.unchecked(() -> Thread.sleep(userConfig.getExecutionInterval()));

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
