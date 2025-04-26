package com.lhmd.rechnerarchitektur.properties;

import com.lhmd.rechnerarchitektur.changes.ChangeManager;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.beans.property.BooleanPropertyBase;

import java.util.Objects;

public class BitPointerProperty extends BooleanPropertyBase {
    private final IntBox intBox;
    private final int bitIndex;
    private final ChangeManager changeManager;

    private boolean isChanged;

    public BitPointerProperty(IntBox intBox, int bitIndex) {
        this.intBox = Objects.requireNonNull(intBox);
        this.bitIndex = IntUtils.requireValidBitIndex(bitIndex);
        this.changeManager = new ChangeManager();

        intBox.changedEvent().addListener(this::onIntBoxChanged);
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void resetChanged() {
        isChanged = false;
    }

    @Override
    public IntBox getBean() {
        return intBox;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    protected void invalidated() {
        if (changeManager.isChanging()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            isChanged = true;
            intBox.set(IntUtils.changeBit(intBox.get(), bitIndex, get()));
        }
    }

    private void onIntBoxChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        try (var ignored = changeManager.beginChange()) {
            isChanged = true;
            set(IntUtils.isBitSet(newValue, bitIndex));
        }
    }
}
