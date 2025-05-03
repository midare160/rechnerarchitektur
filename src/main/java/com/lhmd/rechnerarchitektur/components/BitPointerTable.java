package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.beans.NamedArg;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.*;

public class BitPointerTable extends ScrollPane {
    private final int numberOfBits;
    private final VBox rowsVBox;
    private final List<BitPointerRow> rows;
    private final DoubleProperty currentNameLabelMaxWidth;

    public BitPointerTable(@NamedArg("numberOfBits") int numberOfBits) {
        this.numberOfBits = IntUtils.requireLargerZero(numberOfBits);
        this.rowsVBox = new VBox();
        this.rows = new ArrayList<>();
        this.currentNameLabelMaxWidth = new SimpleDoubleProperty();

        setFitToWidth(true);
        setContent(rowsVBox);
    }

    public List<BitPointerRow> getRows() {
        return rows;
    }

    public void addRow(IntBox intBox, String name) {
        var row = new BitPointerRow(numberOfBits, name);
        row.setData(intBox);

        // Make sure that all the name labels in a table have the same width for alignment
        row.nameLabel().minWidthProperty().bind(currentNameLabelMaxWidth);
        row.nameLabel().widthProperty().addListener(this::onRowNameLabelWidthChanged);

        rows.add(row);
        rowsVBox.getChildren().add(row);
    }

    public void resetChanged() {
        for (var row : rows) {
            row.resetChanged();
        }
    }

    private void onRowNameLabelWidthChanged(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        var newDoubleValue = newValue.doubleValue();

        if (newDoubleValue > currentNameLabelMaxWidth.get()) {
            currentNameLabelMaxWidth.set(newDoubleValue);
        }
    }
}
