package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.registers.StatusRegister;
import javafx.beans.property.*;

import java.util.Set;
import java.util.stream.*;

public class DataMemory {
    private static final int BANK_SIZE = 128;
    private static final Set<Integer> MIRRORED_ADDRESSES;

    private static DataMemory instance;

    static {
        var sfrMirrors = IntStream.of(0x02, 0x03, 0x04, 0x0A, 0x0B);
        var gprMirrors = IntStream.range(0x0C, 0x50);

        MIRRORED_ADDRESSES = IntStream.concat(sfrMirrors, gprMirrors)
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        instance = new DataMemory();
    }

    public static DataMemory instance() {
        return instance;
    }

    public static void reset() {
        instance = new DataMemory();
    }

    private final IntegerProperty[] registers;
    private final StatusRegister statusRegister;

    private DataMemory() {
        registers = getRegisters();
        statusRegister = new StatusRegister(registers[0x03]);
    }

    public StatusRegister status() {
        return statusRegister;
    }

    public IntegerProperty getRegister(int address) {
        var absoluteAddress = IntUtils.changeBit(address, 7, statusRegister.getRP0());

        return registers[absoluteAddress];
    }

    private IntegerProperty[] getRegisters() {
        var registerArray = new SimpleIntegerProperty[2 * BANK_SIZE];

        for (var i = 0x00; i < registerArray.length; i++) {
            // Address was already mirrored
            if (registerArray[i] != null) {
                continue;
            }

            var property = new SimpleIntegerProperty();

            registerArray[i] = property;

            if (MIRRORED_ADDRESSES.contains(i)) {
                registerArray[i + BANK_SIZE] = property;
            }
        }

        return registerArray;
    }
}
