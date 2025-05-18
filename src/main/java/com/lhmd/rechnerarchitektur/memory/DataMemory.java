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

    private final StatusRegister statusRegister;
    private final IntBox[] registers;

    public DataMemory(List<SpecialRegister> specialRegisters, StatusRegister statusRegister) {
        this.statusRegister = statusRegister;
        this.registers = createInitialRegisters(specialRegisters);
    }

    @EventListener(ResetEvent.class)
    public void handleReset() {
        for (var register : registers) {
            register.set(0);
        }
    }

    public IntBox getRegister(int address) {
        // Indirect addressing
        if (Math.floorMod(address, BANK_SIZE) != 0) {
            // Used for selecting bank 0/1
            address = IntUtils.changeBit(address, 7, statusRegister.getRP0());
        }

        return registers[address];
    }

    public List<IntBox> registers() {
        return List.of(registers);
    }

    private IntBox[] createInitialRegisters(List<SpecialRegister> specialRegisters) {
        var specialRegistersMap = getSpecialRegistersMap(specialRegisters);
        var mirroredAddresses = getMirroredAddresses(specialRegistersMap);
        var registerArray = new IntBox[MAX_SIZE];

        for (var i = 0; i < registerArray.length; i++) {
            // Address was already mirrored
            if (registerArray[i] != null) {
                continue;
            }

            IntBox register = specialRegistersMap.get(i);

            if (register == null) {
                register = new IntBox();
            }

            registerArray[i] = register;

            if (mirroredAddresses.contains(i)) {
                registerArray[i + BANK_SIZE] = register;
            }
        }

        return registerArray;
    }

    private Map<Integer, SpecialRegister> getSpecialRegistersMap(List<SpecialRegister> specialRegisters) {
        return specialRegisters.stream()
                .collect(Collectors.toUnmodifiableMap(SpecialRegister::getAddress, r -> r));
    }

    /**
     * Returns all addresses that map to the same registers in both banks (0/1).
     */
    private Set<Integer> getMirroredAddresses(Map<Integer, SpecialRegister> specialRegisters) {
        var sfrMirrors = specialRegisters.entrySet()
                .stream()
                .filter(e -> e.getValue().isMirrored())
                .map(Map.Entry::getKey);

        var gprMirrors = IntStream.range(0x0C, 0x50).boxed();

        return Stream.concat(sfrMirrors, gprMirrors)
                .collect(Collectors.toUnmodifiableSet());
    }
}
