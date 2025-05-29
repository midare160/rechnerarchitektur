package com.lhmd.rechnerarchitektur.pins;

import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.time.Timer;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PinManager {
    private final Timer timer;
    private final IntconRegister intconRegister;
    private final OptionRegister optionRegister;
    private final List<Pin> raPins;
    private final List<Pin> rbPins;

    public PinManager(
            Timer timer,
            TrisARegister trisARegister,
            TrisBRegister trisBRegister,
            PortARegister portARegister,
            PortBRegister portBRegister,
            IntconRegister intconRegister,
            OptionRegister optionRegister) {
        this.timer = timer;
        this.intconRegister = intconRegister;
        this.optionRegister = optionRegister;
        this.raPins = createPins(trisARegister, portARegister, PinType.A);
        this.rbPins = createPins(trisBRegister, portBRegister, PinType.B);

        addChangeListeners();
    }

    public List<Pin> getPins(PinType type) {
        return type == PinType.A ? raPins : rbPins;
    }

    private List<Pin> createPins(IntBox trisRegister, IntBox portRegister, PinType type) {
        var pinArray = new Pin[type.pinCount()];

        for (var i = 0; i < pinArray.length; i++) {
            pinArray[i] = new Pin(trisRegister, portRegister, i);
        }

        return List.of(pinArray);
    }

    private void addChangeListeners() {
        var ra4 = raPins.get(4);
        ra4.onValueChanged().addListener((o, n) -> onRa4PinChanged(ra4));

        var rb0 = rbPins.getFirst();
        rb0.onValueChanged().addListener((o, n) -> onRb0PinChanged(rb0));

        for (var i = 4; i <= 7; i++) {
            var pin = rbPins.get(i);
            pin.onValueChanged().addListener((o, n) -> onRb47PinChanged(pin));
        }
    }

    private void onRa4PinChanged(Pin pin) {
        if (optionRegister.getT0CS() && pin.getValue() != optionRegister.getT0SE()) {
            timer.increment();
        }
    }

    private void onRb0PinChanged(Pin pin) {
        if (pin.getDirection() != PinDirection.IN) {
            return;
        }

        if (pin.getValue() == optionRegister.getINTEDG()) {
            intconRegister.setINTF(true);
        }
    }

    private void onRb47PinChanged(Pin pin) {
        if (pin.getDirection() != PinDirection.IN) {
            return;
        }

        intconRegister.setRBIF(true);
    }
}
