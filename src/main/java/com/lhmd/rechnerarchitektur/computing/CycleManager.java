package com.lhmd.rechnerarchitektur.computing;

import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CycleManager {
    private final UserConfig userConfig;
    private final IntBox tmr0;
    private final OptionRegister option;

    private int cycles;
    private double runtime;

    public CycleManager(UserConfigService userConfigService, DataMemory dataMemory) {
        this.userConfig = userConfigService.config();
        this.tmr0 = dataMemory.getRegister(SpecialAdresses.TMR0);
        this.option = dataMemory.option();
    }

    @EventListener(ResetEvent.class)
    public synchronized void handleReset() {
        cycles = 0;
        runtime = 0;
    }

    /**
     * Returns the simulated runtime in microseconds (µs).
     */
    public synchronized double getRuntime() {
        return runtime;
    }

    // TODO suspend incrementing for 2 cycles when tmr0 is written to
    public synchronized void addCycle() {
        cycles++;

        // TODO check if assigned to tmr0 or wdt
        if (Math.floorMod(cycles, getPrescalerValue()) == 0) {
            tmr0.set(tmr0.get() + 1);
            addTickTime();
        }
    }

    /**
     * Adds the time that one tick of TMR0 would take to {@code runtime}.
     */
    private void addTickTime() {
        // TODO check if assigned to tmr0 or wdt
        runtime += getPrescalerValue() / (userConfig.getClock() / 4);
    }

    // TODO read actual value
    private int getPrescalerValue() {
        return 1;
    }
}
