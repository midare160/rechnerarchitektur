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

    private final ChangedEvent<PinDirection> directionChanged;
    private final ChangedEvent<Boolean> stateChanged;

    public Pin(IntBox trisRegister, IntBox portRegister, int index) {
        this.trisRegister = Objects.requireNonNull(trisRegister);
        this.portRegister = Objects.requireNonNull(portRegister);
        this.index = IntUtils.requireValidBitIndex(index);
        this.value = new BoolBox();

        this.directionChanged = new ChangedEvent<>();
        this.stateChanged = new ChangedEvent<>();

        this.trisRegister.onChanged().addListener(this::onTrisChanged);
        this.portRegister.onChanged().addListener(this::onPortChanged);
    }

    public boolean getValue() {
        return value.get();
    }

    public ChangedEvent<Boolean> onValueChanged() {
        return value.onChanged();
    }

    public PinDirection getDirection() {
        return getDirection(trisRegister.get());
    }

    public void toggleDirection() {
        var newDirection = getDirection() == PinDirection.OUT ? PinDirection.IN : PinDirection.OUT;
        trisRegister.set(IntUtils.changeBit(trisRegister.get(), index, newDirection == PinDirection.IN));
    }

    public ChangedEvent<PinDirection> onDirectionChanged() {
        return directionChanged;
    }

    public boolean getState() {
        return getState(portRegister.get());
    }

    public void toggleState() {
        portRegister.set(IntUtils.changeBit(portRegister.get(), index, !getState()));
    }

    public ChangedEvent<Boolean> onStateChanged() {
        return stateChanged;
    }

    private void onTrisChanged(Integer oldValue, Integer newValue) {
        var oldDirection = getDirection(oldValue);
        var newDirection = getDirection(newValue);

        if (oldDirection == newDirection) {
            return;
        }

        value.set(getState());
        directionChanged.fire(oldDirection, newDirection);
    }

    private void onPortChanged(Integer oldValue, Integer newValue) {
        var oldState = getState(oldValue);
        var newState = getState(newValue);

        if (oldState == newState) {
            return;
        }

        if (getDirection() == PinDirection.OUT) {
            value.set(newState);
        }

        stateChanged.fire(oldState, newState);
    }

    private PinDirection getDirection(int trisValue) {
        return IntUtils.isBitSet(trisValue, index) ? PinDirection.IN : PinDirection.OUT;
    }

    private boolean getState(int portValue) {
        return IntUtils.isBitSet(portValue, index);
    }
}
