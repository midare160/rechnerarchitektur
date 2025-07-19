package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.time.RuntimeManager;
import com.lhmd.rechnerarchitektur.values.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Component
public class EEMemory implements AutoCloseable {
    public static final int MAX_SIZE = 64;
    public static final int REGISTER_WIDTH = 8;

    private static final String FILE_PATH = "eeprom.bin";

    private final DoubleBox runtime;
    private final EECon1Register eeCon1Register;
    private final EECon2Register eeCon2Register;
    private final EEDataRegister eeDataRegister;
    private final EEAdrRegister eeAdrRegister;
    private final List<IntBox> registers;
    private final ChangeManager changeManager;

    private boolean isEECon2Unlocked;

    public EEMemory(RuntimeManager runtimeManager, EECon1Register eeCon1Register, EECon2Register eeCon2Register, EEDataRegister eeDataRegister, EEAdrRegister eeAdrRegister) {
        this.runtime = runtimeManager.runtime();
        this.eeCon1Register = eeCon1Register;
        this.eeCon2Register = eeCon2Register;
        this.eeDataRegister = eeDataRegister;
        this.eeAdrRegister = eeAdrRegister;

        this.registers = Runner.unchecked(this::loadRegisters);
        this.changeManager = new ChangeManager();

        this.eeCon1Register.onChanged().addListener(this::onEECon1Changed);
        this.eeCon2Register.onChanged().addListener(this::onEECon2Changed);
    }

    @Override
    public void close() throws IOException {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(registers);
        }
    }

    public List<IntBox> registers() {
        return registers;
    }

    @SuppressWarnings("unchecked")
    private List<IntBox> loadRegisters() throws IOException, ClassNotFoundException {
        if (Files.notExists(Path.of(FILE_PATH))) {
            return createInitialRegisters();
        }

        try (var reader = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<IntBox>) reader.readObject();
        }
    }

    private List<IntBox> createInitialRegisters() {
        var registerArray = new IntBox[MAX_SIZE];

        for (var i = 0; i < registerArray.length; i++) {
            registerArray[i] = new IntBox();
        }

        return List.of(registerArray);
    }

    /**
     * To read a data memory location, the user must write the address to the EEADR register and then set control bit RD (EECON1<0>).
     * The data is available, in the very next cycle, in the EEDATA register; therefore it can be read in the next instruction.
     * EEDATA will hold this value until another read or until it is written to by the user (during a write operation).
     * At the completion of the read cycle, the RD bit is cleared.
     */
    private void tryRead() {
        if (!eeCon1Register.getRD()) {
            return;
        }

        var address = eeAdrRegister.get();
        var data = registers.get(address).get();

        eeDataRegister.set(data);

        eeCon1Register.setRD(false);
    }

    /**
     * To write an EEPROM data location, the user must first write the address to the EEADR register and the data to the EEDATA register.
     * Additionally, the WREN bit in EECON1 must be set to enable write.
     * At the completion of the write cycle, the WR bit is cleared and the EE Write Complete Interrupt Flag bit (EEIF) is set.
     */
    private void tryWrite() {
        if (!isEECon2Unlocked || !eeCon1Register.getWR() || !eeCon1Register.getWREN()) {
            return;
        }

        var address = eeAdrRegister.get();
        var data = eeDataRegister.get();

        registers.get(address).set(data);

        // Write cycles have a write time of ~1ms => 1000µs
        runtime.set(runtime.get() + 1000);

        eeCon1Register.setWR(false);
        eeCon1Register.setEEIF(true);
        isEECon2Unlocked = false;
    }

    private void onEECon1Changed(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            tryRead();
            tryWrite();
        }
    }

    private void onEECon2Changed(Integer oldValue, Integer newValue) {
        // The write will not initiate if the this sequence is not exactly followed for each byte
        isEECon2Unlocked = oldValue == 0x55 && newValue == 0xAA;
    }
}
