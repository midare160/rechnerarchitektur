package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.*;
import org.springframework.stereotype.Component;

@Component
public class RuntimeManager {
    private final UserConfig userConfig;
    private final IntBox tmr0Register;
    private final IntconRegister intconRegister;
    private final OptionRegister optionRegister;
    private final DoubleBox runtime;

    private int cycles;

    public RuntimeManager(UserConfigService userConfigService, Tmr0Register tmr0Register, IntconRegister intconRegister, OptionRegister optionRegister) {
        this.userConfig = userConfigService.config();
        this.tmr0Register = tmr0Register;
        this.intconRegister = intconRegister;
        this.optionRegister = optionRegister;
        this.runtime = new DoubleBox();

        tmr0Register.onChanged().addListener(this::onTmr0Changed);
    }

    /**
     * Returns the simulated runtime in microseconds (µs).
     */
    public DoubleBox runtime() {
        return runtime;
    }

    // TODO suspend incrementing for 2 cycles when tmr0 is written to
    public synchronized void addCycle() {
        cycles++;

        // TODO check if assigned to tmr0 or wdt
        if (Math.floorMod(cycles, getPrescalerValue()) == 0) {
            tmr0Register.set((tmr0Register.get() + 1) % DataMemory.REGISTER_MAX_SIZE);
            addTickTime();
        }
    }

    public synchronized void reset() {
        cycles = 0;
        runtime.set(0d);
    }

    /**
     * Adds the time that one tick of TMR0 would take to {@code runtime}.
     */
    private void addTickTime() {
        // TODO check if assigned to tmr0 or wdt
        var micros = getPrescalerValue() / (userConfig.getClock() / 4);
        runtime.set(runtime.get() + micros);
    }

    // TODO read actual value
    private int getPrescalerValue() {
        return 1;
    }

    private void onTmr0Changed(Integer oldValue, Integer newValue) {
        if (oldValue == DataMemory.REGISTER_MAX_SIZE - 1 && newValue == 0) {
            intconRegister.setT0IF(true);
        }
    }
}
