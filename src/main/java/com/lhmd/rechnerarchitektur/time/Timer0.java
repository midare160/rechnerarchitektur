package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.memory.DataMemory;
import com.lhmd.rechnerarchitektur.registers.*;
import org.springframework.stereotype.Component;

@Component
public class Timer0 {
    private final Prescaler prescaler;
    private final Tmr0Register tmr0Register;
    private final IntconRegister intconRegister;
    private final ChangeManager changeManager;

    public Timer0(Prescaler prescaler, Tmr0Register tmr0Register, IntconRegister intconRegister) {
        this.prescaler = prescaler;
        this.tmr0Register = tmr0Register;
        this.intconRegister = intconRegister;

        this.changeManager = new ChangeManager();

        this.tmr0Register.onWritten().addListener(this::onTmr0Written);
        this.tmr0Register.onChanged().addListener(this::onTmr0Changed);
    }

    public void increment() {
        // Prescaler is assigned to Timer0 and did not overflow on increment => TMR0 does not get incremented
        if (prescaler.assignment() == PrescalerAssignment.TIMER && !prescaler.increment()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            tmr0Register.set(Math.floorMod(tmr0Register.get() + 1, DataMemory.REGISTER_MAX_SIZE));
        }
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
