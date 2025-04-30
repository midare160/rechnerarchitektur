package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.PseudoClasses;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class BitPointerCell extends Label {
    private final IntBox intBox;
    private final int bitIndex;
    private final boolean readOnly;

    public BitPointerCell(IntBox intBox, int bitIndex, boolean readOnly) {
        this.intBox = intBox;
        this.bitIndex = IntUtils.requireValidBitIndex(bitIndex);
        this.readOnly = readOnly;

        setOnMouseClicked(this::onMouseClicked);
        intBox.onChanged().addListener(this::onIntBoxChanged);

        setAlignment(Pos.CENTER);
        updateText();
    }

    public void setChanged(boolean changed) {
        pseudoClassStateChanged(PseudoClasses.CHANGED, changed);
    }

    private void onMouseClicked(MouseEvent e) {
        if (!readOnly) {
            intBox.set(IntUtils.changeBit(intBox.get(), bitIndex, !isBitSet()));
        }
    }

    private void onIntBoxChanged(Integer oldValue, Integer newValue) {
        updateText();

        if (IntUtils.isBitSet(oldValue, bitIndex) != IntUtils.isBitSet(newValue, bitIndex)) {
            setChanged(true);
        }
    }

    private boolean isBitSet() {
        return IntUtils.isBitSet(intBox.get(), bitIndex);
    }

    private void updateText() {
        Platform.runLater(() -> setText(isBitSet() ? "1" : "0"));
    }
}
