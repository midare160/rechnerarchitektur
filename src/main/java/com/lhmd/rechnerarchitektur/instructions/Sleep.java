package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.SleepManager;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.time.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * The power-down status bit, PD is cleared.
 * Time-out status bit, TO is set.
 * Watchdog Timer and its prescaler are cleared.
 * The processor is put into SLEEP mode with the oscillator stopped.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Sleep extends InstructionBase {
    private final SleepManager sleepManager;
    private final Prescaler prescaler;
    private final Watchdog watchdog;
    private final StatusRegister statusRegister;

    public Sleep(SleepManager sleepManager, Prescaler prescaler, Watchdog watchdog, StatusRegister statusRegister) {
        this.sleepManager = sleepManager;
        this.prescaler = prescaler;
        this.watchdog = watchdog;
        this.statusRegister = statusRegister;
    }

    @Override
    public void execute() {
        watchdog.reset();

        if (prescaler.assignment() == PrescalerAssignment.WATCHDOG) {
            prescaler.reset();
        }

        statusRegister.setTO(true);
        statusRegister.setPD(false);

        sleepManager.sleep();
    }
}
