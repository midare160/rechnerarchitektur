package com.lhmd.rechnerarchitektur.instructions;

import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import com.lhmd.rechnerarchitektur.time.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * CLRWDT instruction resets the Watchdog Timer.
 * It also resets the prescaler of the WDT.
 * Status bits TO and PD are set.
 */
@Component
@Scope(InstructionBase.SCOPE)
public class Clrwdt extends InstructionBase {
    private final Prescaler prescaler;
    private final Watchdog watchdog;
    private final StatusRegister statusRegister;

    public Clrwdt(Prescaler prescaler, Watchdog watchdog, StatusRegister statusRegister) {
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
        statusRegister.setPD(true);
    }
}
