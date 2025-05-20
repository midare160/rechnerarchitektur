package com.lhmd.rechnerarchitektur.pins;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ChangedEvent;
import com.lhmd.rechnerarchitektur.values.*;

import java.util.Objects;

public class Pin {
    private final IntBox trisRegister;
    private final IntBox portRegister;
    private final int index;
    private final BoolBox value;

    public Pin(IntBox trisRegister, IntBox portRegister, int index) {
        this.trisRegister = Objects.requireNonNull(trisRegister);
        this.portRegister = Objects.requireNonNull(portRegister);
        this.index = IntUtils.requireValidBitIndex(index);
        this.value = new BoolBox();

        this.trisRegister.onChanged().addListener(this::onTrisChanged);
        this.portRegister.onChanged().addListener(this::onPortChanged);
    }

    public ChangedEvent<Boolean> onChanged() {
        return value.onChanged();
    }

    public PinDirection direction() {
        return getDirection(trisRegister.get());
    }

    public boolean state() {
        return getState(portRegister.get());
    }

    private void onTrisChanged(Integer oldValue, Integer newValue) {
        if (getDirection(oldValue) != getDirection(newValue)) {
            value.set(state());
        }
    }

    private void onPortChanged(Integer oldValue, Integer newValue) {
        var newState = getState(newValue);

        if (getState(oldValue) != newState && direction() == PinDirection.OUT) {
            value.set(newState);
        }
    }

    private PinDirection getDirection(int trisValue) {
        return IntUtils.isBitSet(trisValue, index) ? PinDirection.IN : PinDirection.OUT;
    }

    private boolean getState(int portValue) {
        return IntUtils.isBitSet(portValue, index);
    }
}
