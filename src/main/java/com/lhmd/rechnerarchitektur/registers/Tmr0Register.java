package com.lhmd.rechnerarchitektur.registers;

import com.lhmd.rechnerarchitektur.events.ActionEvent;
import org.springframework.stereotype.Component;

@Component
public class Tmr0Register extends SpecialRegister {
    private final ActionEvent onWritten;

    public Tmr0Register() {
        onWritten = new ActionEvent();
    }

    @Override
    public int getAddress() {
        return 0x01;
    }

    @Override
    public void setValue(Integer value) {
        super.setValue(value);
        onWritten.fire();
    }

    public ActionEvent onWritten() {
        return onWritten;
    }
}
