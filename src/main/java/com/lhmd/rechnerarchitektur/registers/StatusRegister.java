package com.lhmd.rechnerarchitektur.registers;

import javafx.beans.property.*;

public class StatusRegister {
    private static StatusRegister instance = new StatusRegister();

    public static StatusRegister instance() {
        return instance;
    }

    public static void reset() {
        instance = new StatusRegister();
    }

    private final BooleanProperty rp0 = new SimpleBooleanProperty();
    private final BooleanProperty to = new SimpleBooleanProperty(true);
    private final BooleanProperty pd = new SimpleBooleanProperty(true);
    private final BooleanProperty z = new SimpleBooleanProperty();
    private final BooleanProperty dc = new SimpleBooleanProperty();
    private final BooleanProperty c = new SimpleBooleanProperty();

    public BooleanProperty RP0() {
        return rp0;
    }

    public BooleanProperty TO() {
        return to;
    }

    public BooleanProperty PD() {
        return pd;
    }

    public BooleanProperty Z() {
        return z;
    }

    public BooleanProperty DC() {
        return dc;
    }

    public BooleanProperty C() {
        return c;
    }
}
