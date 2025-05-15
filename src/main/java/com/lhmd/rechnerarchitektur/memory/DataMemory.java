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
    public static final int REGISTER_SIZE = (int) Math.pow(2, REGISTER_WIDTH) - 1;

    private static final Set<Integer> MIRRORED_ADDRESSES;

    static {
        var sfrMirrors = IntStream.of(0x02, 0x03, 0x04, 0x0A, 0x0B);
        var gprMirrors = IntStream.range(0x0C, 0x50);

        MIRRORED_ADDRESSES = IntStream.concat(sfrMirrors, gprMirrors)
                .boxed()
                .collect(Collectors.toUnmodifiableSet());
    }

    private final IntBox[] registers;
    private final StatusRegister statusRegister;

    public DataMemory() {
        registers = getInitialRegisters();
        statusRegister = new StatusRegister(registers[0x03]);
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

    public IntBox getRegister(int address) {
        // Used for selecting bank 0/1
        var absoluteAddress = IntUtils.changeBit(address, 7, statusRegister.getRP0());

        return registers[absoluteAddress];
    }

    public List<IntBox> registers() {
        return List.of(registers);
    }

    private IntBox[] getInitialRegisters() {
        // PIC16FX is partitioned into 2 banks
        var registerArray = new IntBox[MAX_SIZE];

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
