package com.lhmd.rechnerarchitektur.registers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;

public class StatusRegister2 {
    private final IntegerProperty property;

    public StatusRegister2(IntegerProperty property) {
        this.property = property;
    }

    public void addListener(ChangeListener<? super Number> listener) {
        property.addListener(listener);
    }

    public void removeListener(ChangeListener<? super Number> listener) {
        property.removeListener(listener);
    }
}
