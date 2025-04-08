package com.lhmd.rechnerarchitektur.memory;

import javafx.beans.property.SimpleIntegerProperty;

public class DataMemory {
    private static final int BANK_SIZE = 128;

    private final SimpleIntegerProperty[] bank1;
    private final SimpleIntegerProperty[] bank2;
    private SimpleIntegerProperty[] activeBank;

    private DataMemory() {
        bank1 = new SimpleIntegerProperty[BANK_SIZE];
        bank2 = new SimpleIntegerProperty[BANK_SIZE];

        for (int i = 0; i < BANK_SIZE; i++) {
            bank1[i] = new SimpleIntegerProperty();
            bank2[i] = new SimpleIntegerProperty();
        }

        activeBank = bank1;
    }
}
