package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.values.IntBox;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.*;

import java.util.Set;
import java.util.stream.*;

public class DataMemory {
    private static final int BANK_SIZE = 128;
    private static final Set<Integer> MIRRORED_ADDRESSES;

    static {
        var sfrMirrors = IntStream.of(0x02, 0x03, 0x04, 0x0A, 0x0B);
        var gprMirrors = IntStream.range(0x0C, 0x50);

        MIRRORED_ADDRESSES = IntStream.concat(sfrMirrors, gprMirrors)
                .boxed()
                .collect(Collectors.toUnmodifiableSet());
    }

    private final IntBox[] registers;
    private final IntBox wRegister;
    private final StatusRegister statusRegister;

    public DataMemory() {
        registers = getInitialRegisters();
        wRegister = new IntBox();
        statusRegister = new StatusRegister(registers[0x03]);
    }

    public IntBox W() {
        return wRegister;
    }

    public StatusRegister status() {
        return statusRegister;
    }

    public IntBox getRegister(int address) {
        var absoluteAddress = IntUtils.changeBit(address, 7, statusRegister.getRP0());

        return registers[absoluteAddress];
    }

    public void reset() {
        wRegister.set(0);

        for (var register : registers) {
            register.set(0);
        }
    }

    private IntBox[] getInitialRegisters() {
        // PIC16FX is partitioned into 2 banks
        var registerArray = new IntBox[2 * BANK_SIZE];

        for (var i = 0x00; i < registerArray.length; i++) {
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
