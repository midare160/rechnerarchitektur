package com.lhmd.rechnerarchitektur.time;

import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.registers.OptionRegister;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class Prescaler {
    private final OptionRegister optionRegister;

    private int counter;

    public Prescaler(OptionRegister optionRegister) {
        this.optionRegister = optionRegister;
    }

    @EventListener
    @Order(EventOrders.EXECUTION)
    public void handleReset(ResetEvent event) {
        reset();
    }

    public PrescalerAssignment assignment() {
        return optionRegister.getPSA() ? PrescalerAssignment.WATCHDOG : PrescalerAssignment.TIMER;
    }

    /**
     * Returns the current prescaler rate depending on the assignment.
     */
    public int rate() {
        var exponent = optionRegister.getPS02();

        if (assignment() == PrescalerAssignment.TIMER) {
            exponent++;
        }

        return (int) Math.pow(2, exponent);
    }

    /**
     * Increments the internal counter by one.
     *
     * @return {@code true} if the counter overflowed, otherwise {@code false}
     */
    public boolean increment() {
        counter = Math.floorMod(counter + 1, rate());

        return counter == 0;
    }

    public void reset() {
        counter = 0;
    }
}
