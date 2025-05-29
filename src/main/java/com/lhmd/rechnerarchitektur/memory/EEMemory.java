package com.lhmd.rechnerarchitektur.memory;

import com.lhmd.rechnerarchitektur.registers.*;
import com.lhmd.rechnerarchitektur.values.IntBox;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;
import java.util.List;

@Component
public class EEMemory implements AutoCloseable {
    public static final int MAX_SIZE = 64;

    private static final String FILE_PATH = "eeprom.txt";

    private final EECon1Register eeCon1Register;
    private final EECon2Register eeCon2Register;
    private final EEDataRegister eeDataRegister;
    private final EEAdrRegister eeAdrRegister;
    private final IntBox[] registers;

    private boolean isEECon2Unlocked;

    public EEMemory(EECon1Register eeCon1Register, EECon2Register eeCon2Register, EEDataRegister eeDataRegister, EEAdrRegister eeAdrRegister)
            throws IOException, ClassNotFoundException {
        this.eeCon1Register = eeCon1Register;
        this.eeCon2Register = eeCon2Register;
        this.eeDataRegister = eeDataRegister;
        this.eeAdrRegister = eeAdrRegister;

        this.registers = loadRegisters();

        this.eeCon2Register.onChanged().addListener(this::onEECon2Changed);
    }

    @Override
    public void close() throws IOException {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(registers);
        }
    }

    public List<IntBox> registers() {
        return List.of(registers);
    }

    private IntBox[] loadRegisters() throws IOException, ClassNotFoundException {
        if (Files.notExists(Path.of(FILE_PATH))) {
            return createInitialRegisters();
        }

        try (var reader = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (IntBox[]) reader.readObject();
        }
    }

    private IntBox[] createInitialRegisters() {
        var registers = new IntBox[MAX_SIZE];

        for (int i = 0; i < registers.length; i++) {
            registers[i] = new IntBox();
        }

        return registers;
    }

    private void onEECon2Changed(Integer oldValue, Integer newValue) {
        isEECon2Unlocked = oldValue == 0x55 && newValue == 0xAA;
    }
}
