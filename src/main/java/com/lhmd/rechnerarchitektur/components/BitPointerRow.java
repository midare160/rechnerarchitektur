package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.*;

public class BitPointerRow extends HBox {
    private final List<BitPointerCell> cells;

    public BitPointerRow(IntBox intBox, int numberOfBits, String name) {
        this.cells = new ArrayList<>(numberOfBits);

        setMaxWidth(Double.MAX_VALUE);

        var nameLabel = new Label(name);
        nameLabel.getStyleClass().add("monospaced");
        addChildLabel(nameLabel);

        for (var i = numberOfBits - 1; i >= 0; i--) {
            var cell = new BitPointerCell(intBox, i);
            cells.add(cell);
            addChildLabel(cell);
        }
    }

    public void resetChanged() {
        for (var cell : cells) {
            cell.setChanged(false);
        }
    }

    private void addChildLabel(Label label) {
        label.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.ALWAYS);

        getChildren().add(label);
    }
}
