package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.beans.NamedArg;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.*;

public class BitPointerTable extends ScrollPane {
    private final int numberOfBits;
    private final VBox rowsVBox;
    private final List<BitPointerRow> rows;

    public BitPointerTable(@NamedArg("numberOfBits") int numberOfBits) {
        this.numberOfBits = IntUtils.requireLargerZero(numberOfBits);
        this.rowsVBox = new VBox();
        this.rows = new ArrayList<>();

        setFitToWidth(true);
        setContent(rowsVBox);
    }

    public void addRow(IntBox intBox, String name) {
        var row = new BitPointerRow(intBox, numberOfBits, name);

        rows.add(row);
        rowsVBox.getChildren().add(row);
    }

    public void resetChanged() {
        for (var row : rows) {
            row.resetChanged();
        }
    }
}
