package com.lhmd.rechnerarchitektur.models;

import com.lhmd.rechnerarchitektur.properties.BitPointerProperty;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.beans.property.*;

public class BitPointerSet {
    private final StringProperty nameProperty;
    private final BitPointerProperty[] pointers;

    public BitPointerSet(String name, IntBox intBox) {
        nameProperty = new SimpleStringProperty(name);
        pointers = new BitPointerProperty[32];

        for (var i = 0; i < pointers.length; i++) {
            pointers[i] = new BitPointerProperty(intBox, i);
        }
    }

    public BitPointerProperty get(int index) {
        return pointers[index];
    }

    public StringProperty nameProperty() {
        return nameProperty;
    }
}
