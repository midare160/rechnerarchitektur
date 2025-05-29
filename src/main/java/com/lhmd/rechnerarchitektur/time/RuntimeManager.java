package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.*;
import org.springframework.stereotype.Component;

@Component
public class RuntimeManager {
    private final UserConfig userConfig;
    private final Prescaler prescaler;
    private final Tmr0Register tmr0Register;
    private final OptionRegister optionRegister;
    private final IntconRegister intconRegister;
    private final DoubleBox runtime;
    private final ChangeManager changeManager;

    public RuntimeManager(
            UserConfigService userConfigService,
            Prescaler prescaler,
            Tmr0Register tmr0Register,
            OptionRegister optionRegister,
            IntconRegister intconRegister) {
        this.userConfig = userConfigService.config();
        this.prescaler = prescaler;
        this.tmr0Register = tmr0Register;
        this.optionRegister = optionRegister;
        this.intconRegister = intconRegister;

        this.runtime = new DoubleBox();
        this.changeManager = new ChangeManager();

        this.tmr0Register.onWritten().addListener(this::onTmr0Written);
        this.tmr0Register.onChanged().addListener(this::onTmr0Changed);
    }

    /**
     * Returns the simulated runtime in microseconds (µs).
     */
    public DoubleBox runtime() {
        return runtime;
    }

    public synchronized void addCycle() {
        var micros = 1.0 / (userConfig.getClock() / 4);
        runtime.set(runtime.get() + micros);

        // Timer mode is selected by clearing the T0CS bit.
        // In timer mode, the Timer0 module will increment every instruction cycle
        if (!optionRegister.getT0CS()) {
            incrementTimer();
        }
    }

    public synchronized void incrementTimer() {
        // Prescaler is assigned to Timer0 and did not overflow on increment => TMR0 does not get incremented
        if (prescaler.assignment() == PrescalerAssignment.TIMER && !prescaler.increment()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            tmr0Register.set(Math.floorMod(tmr0Register.get() + 1, DataMemory.REGISTER_MAX_SIZE));
        }
    }

    public void reset() {
        runtime.set(0);
    }

    private void onTmr0Written() {
        // When assigned to the Timer0 Module, all instructions writing to the Timer0 Module will clear the prescaler.
        if (!changeManager.isChanging() && prescaler.assignment() == PrescalerAssignment.TIMER) {
            prescaler.reset();
        }
    }

    private void onTmr0Changed(Integer oldValue, Integer newValue) {
        // The TMR0 interrupt is generated when the TMR0 register overflows from 0xFF to 0x00
        if (oldValue == DataMemory.REGISTER_MAX_SIZE - 1 && newValue == 0) {
            intconRegister.setT0IF(true);
        }
    }
}
