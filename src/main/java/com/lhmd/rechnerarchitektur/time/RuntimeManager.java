package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.EventOrders;
import com.lhmd.rechnerarchitektur.events.ResetEvent;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class RuntimeManager {
    private final UserConfig userConfig;
    private final Prescaler prescaler;
    private final IntBox tmr0Register;
    private final OptionRegister optionRegister;
    private final IntconRegister intconRegister;
    private final DoubleBox runtime;
    private final ChangeManager changeManager;

    private int currentWaitCycles;

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

        tmr0Register.onChanged().addListener(this::onTmr0Changed);
    }

    @EventListener
    @Order(EventOrders.EXECUTION)
    public void handleReset(ResetEvent event) {
        currentWaitCycles = 0;
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
        // If the TMR0 register is written, the increment is inhibited for the following two cycles
        if (currentWaitCycles > 0) {
            currentWaitCycles--;
            return;
        }

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

    private void onTmr0Changed(Integer oldValue, Integer newValue) {
        if (!changeManager.isChanging()) {
            currentWaitCycles = 1; // TODO should be 2
            prescaler.reset(); // Data sheet states
            // "When assigned to the Timer0 Module, all instructions writing to the Timer0 Module (e.g., CLRF 1, MOVWF 1, BSF 1,x ....etc.) will clear the prescaler.",
            // but Lehmann sheet does not
        }

        // The TMR0 interrupt is generated when the TMR0 register overflows from 0xFF to 0x00
        if (oldValue == DataMemory.REGISTER_MAX_SIZE - 1 && newValue == 0) {
            intconRegister.setT0IF(true);
        }
    }
}
