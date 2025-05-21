package com.lhmd.rechnerarchitektur.pins;

import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PinManager {
    private final Pin[] raPins;
    private final Pin[] rbPins;

    public PinManager(TrisARegister trisARegister, TrisBRegister trisBRegister, PortARegister portARegister, PortBRegister portBRegister) {
        raPins = createPins(trisARegister, portARegister, PinType.A);
        rbPins = createPins(trisBRegister, portBRegister, PinType.B);
    }

    public List<Pin> getPins(PinType type) {
        return List.of(type == PinType.A ? raPins : rbPins);
    }

    private Pin[] createPins(IntBox trisRegister, IntBox portRegister, PinType type) {
        var pinArray = new Pin[type.pinCount()];

        for (var i = 0; i < pinArray.length; i++) {
            pinArray[i] = new Pin(trisRegister, portRegister, i);
        }

        return pinArray;
    }
}
