package com.lhmd.rechnerarchitektur.registers;

import javafx.beans.property.*;

public class WRegister {
    private static final IntegerProperty instance = new SimpleIntegerProperty();

    public static IntegerProperty instance() {
        return instance;
    }

    public static void reset() {
        instance.set(0);
    }
}
