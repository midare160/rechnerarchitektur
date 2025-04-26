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

    public BitPointerProperty(IntBox intBox, int bitIndex) {
        this.intBox = Objects.requireNonNull(intBox);
        this.bitIndex = IntUtils.requireValidBitIndex(bitIndex);
        this.changeManager = new ChangeManager();

        intBox.changedEvent().addListener(this::onIntBoxChanged);
    }

    public boolean isChanged() {
        return changeManager.isChanged();
    }

    public void resetChanged() {
        changeManager.setChanged(false);
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

        changeManager.setChanged(true);

        try (var ignored = changeManager.beginChange()) {
            intBox.set(IntUtils.changeBit(intBox.get(), bitIndex, get()));
        }
    }

    private void onIntBoxChanged(Integer oldValue, Integer newValue) {
        if (changeManager.isChanging()) {
            return;
        }

        changeManager.setChanged(true);

        try (var ignored = changeManager.beginChange()) {
            set(IntUtils.isBitSet(newValue, bitIndex));
        }
    }
}
