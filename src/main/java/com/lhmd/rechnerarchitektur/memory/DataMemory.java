package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.common.IntBox;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;

import java.util.Set;
import java.util.stream.*;

public class DataMemory {
    private static final int BANK_SIZE = 128;
    private static final Set<Integer> MIRRORED_ADDRESSES;
    private static final DataMemory INSTANCE;

    static {
        var sfrMirrors = IntStream.of(0x02, 0x03, 0x04, 0x0A, 0x0B);
        var gprMirrors = IntStream.range(0x0C, 0x50);

        MIRRORED_ADDRESSES = IntStream.concat(sfrMirrors, gprMirrors)
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        INSTANCE = new DataMemory();
    }

    public static DataMemory instance() {
        return INSTANCE;
    }

    private final IntBox[] registers;
    private final StatusRegister statusRegister;

    private DataMemory() {
        registers = getInitialRegisters();
        statusRegister = new StatusRegister(registers[0x03]);
    }

    public StatusRegister status() {
        return statusRegister;
    }

    public IntBox getRegister(int address) {
        var absoluteAddress = IntUtils.changeBit(address, 7, statusRegister.getRP0());

        return registers[absoluteAddress];
    }

    public void reset() {
        for (var register : INSTANCE.registers) {
            register.set(0);
        }
    }

    private IntBox[] getInitialRegisters() {
        // PIC16X is partitioned into 2 banks
        var registerArray = new IntBox[2 * BANK_SIZE];

        for (var i = 0x00; i < registerArray.length; i++) {
            // Address was already mirrored
            if (registerArray[i] != null) {
                continue;
            }

            var property = new IntBox();

            registerArray[i] = property;

            if (MIRRORED_ADDRESSES.contains(i)) {
                registerArray[i + BANK_SIZE] = property;
            }
        }

        return registerArray;
    }
}
