package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.*;
import org.springframework.stereotype.Component;

@Component
public class RuntimeManager {
    private final UserConfig userConfig;
    private final Timer timer;
    private final Watchdog watchdog;
    private final OptionRegister optionRegister;
    private final DoubleBox runtime;

    public RuntimeManager(UserConfigService userConfigService, Timer timer, Watchdog watchdog, OptionRegister optionRegister) {
        this.userConfig = userConfigService.config();
        this.timer = timer;
        this.watchdog = watchdog;
        this.optionRegister = optionRegister;

        this.runtime = new DoubleBox();
    }

    /**
     * Returns the simulated runtime in microseconds (µs).
     */
    public DoubleBox runtime() {
        return runtime;
    }

    public void addCycle() {
        var micros = 1.0 / (userConfig.getClock() / 4);
        runtime.set(runtime.get() + micros);

        // Timer mode is selected by clearing the T0CS bit.
        // In timer mode, the Timer0 module will increment every instruction cycle
        if (!optionRegister.getT0CS()) {
            timer.increment();
        }

        if (userConfig.isWatchdogEnabled()) {
            watchdog.increment();
        }
    }

    public void reset() {
        runtime.set(0);
    }
}
