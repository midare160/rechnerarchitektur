package com.lhmd.rechnerarchitektur.models;

import com.lhmd.rechnerarchitektur.properties.BitPointerProperty;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.beans.property.*;
import javafx.fxml.FXML;

public class BitPointerSet {
    private final StringProperty nameProperty;
    private final IntBox intBox;
    private final BitPointerProperty[] pointers;

    public BitPointerSet(String name, IntBox intBox) {
        this.nameProperty = new SimpleStringProperty(name);
        this.intBox = intBox;
        this.pointers = new BitPointerProperty[32];
    }

    public BitPointerProperty get(int index) {
        var pointer = pointers[index];

        if (pointer == null) {
            pointer = new BitPointerProperty(intBox, index);
            pointers[index] = pointer;
        }

        return pointer;
    }

    @FXML
    public StringProperty nameProperty() {
        return nameProperty;
    }
}
