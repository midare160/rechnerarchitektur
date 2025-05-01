package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.PseudoClasses;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.events.ChangeListener;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class BitPointerCell extends Label {
    private final int bitIndex;
    private final boolean readOnly;
    private final ChangeListener<Integer> changeListener;

    private IntBox intBox;
    private boolean previousValue;

    public BitPointerCell(@NamedArg("bitIndex") int bitIndex, @NamedArg("readOnly") boolean readOnly) {
        this.bitIndex = IntUtils.requireValidBitIndex(bitIndex);
        this.readOnly = readOnly;
        this.changeListener = this::onIntBoxChanged;

        setAlignment(Pos.CENTER);
        setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this, Priority.ALWAYS);

        setOnMouseClicked(this::onMouseClicked);

        updateText();
    }

    public void setData(IntBox intBox) {
        if (this.intBox != null) {
            this.intBox.onChanged().removeListener(changeListener);
        }

        this.intBox = intBox;
        this.intBox.onChanged().addListener(changeListener);

        resetChanged();
    }

    public void resetChanged() {
        previousValue = isBitSet();
        setChanged(false);
    }

    private void onMouseClicked(MouseEvent e) {
        if (!readOnly && intBox != null) {
            intBox.set(IntUtils.changeBit(intBox.get(), bitIndex, !isBitSet()));
        }
    }

    private void onIntBoxChanged(Integer oldValue, Integer newValue) {
        updateText();

        var isChanged = previousValue != IntUtils.isBitSet(newValue, bitIndex);
        setChanged(isChanged);
    }

    private void setChanged(boolean changed) {
        pseudoClassStateChanged(PseudoClasses.CHANGED, changed);
    }

    private boolean isBitSet() {
        return intBox != null && IntUtils.isBitSet(intBox.get(), bitIndex);
    }

    private void updateText() {
        var text = isBitSet() ? "1" : "0";
        Platform.runLater(() -> setText(text));
    }
}
