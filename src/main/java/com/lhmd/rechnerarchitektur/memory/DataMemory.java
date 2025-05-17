package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.events.ResetEvent;
import com.lhmd.rechnerarchitektur.values.IntBox;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.*;

@Component
public class DataMemory {
    public static final int MAX_SIZE = 256;
    public static final int BANK_SIZE = MAX_SIZE / 2;
    public static final int REGISTER_WIDTH = 8;
    public static final int REGISTER_MAX_SIZE = (int) Math.pow(2, REGISTER_WIDTH);

    private static final Set<Integer> MIRRORED_ADDRESSES;

    static {
        var sfrMirrors = SpecialAdresses.MIRRORED.stream();
        var gprMirrors = IntStream.range(0x0C, 0x50).boxed();

        MIRRORED_ADDRESSES = Stream.concat(sfrMirrors, gprMirrors)
                .collect(Collectors.toUnmodifiableSet());
    }

    private final IntBox[] registers;
    private final StatusRegister statusRegister;
    private final IndfRegister indfRegister;

    public DataMemory() {
        registers = createInitialRegisters();
        statusRegister = new StatusRegister(registers[SpecialAdresses.STATUS]);
        indfRegister = new IndfRegister(this);
    }

    @EventListener(ResetEvent.class)
    public void handleReset() {
        for (var register : registers) {
            register.set(0);
        }
    }

    public StatusRegister status() {
        return statusRegister;
    }

    public IndfRegister indf() {
        return indfRegister;
    }

    public IntBox getRegister(int address) {
        // Indirect addressing
        if (Math.floorMod(address, BANK_SIZE) != SpecialAdresses.INDF) {
            // Used for selecting bank 0/1
            address = IntUtils.changeBit(address, 7, statusRegister.getRP0());
        }

        return registers[address];
    }

    public List<IntBox> registers() {
        return List.of(registers);
    }

    private IntBox[] createInitialRegisters() {
        // PIC16FX is partitioned into 2 banks
        var registerArray = new IntBox[MAX_SIZE];

        for (var i = 0; i < registerArray.length; i++) {
            // Address was already mirrored
            if (registerArray[i] != null) {
                continue;
            }

            var register = new IntBox();
            registerArray[i] = register;

            if (MIRRORED_ADDRESSES.contains(i)) {
                registerArray[i + BANK_SIZE] = register;
            }
        }

        return registerArray;
    }
}
