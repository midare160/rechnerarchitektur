package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.events.ChangeListener;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.util.Duration;

public class BitPointerRow extends HBox {
    private final BitPointerCell[] cells;
    private final String name;
    private final Tooltip tooltip;
    private final ChangeListener<Integer> changeListener;

    private IntBox intBox;

    public BitPointerRow(@NamedArg("numberOfBits") int numberOfBits, @NamedArg("name") String name, @NamedArg("readOnly") boolean readOnly) {
        this.cells = new BitPointerCell[numberOfBits];
        this.name = name;
        this.tooltip = new Tooltip();
        this.changeListener = (o, n) -> updateTooltipText();

        setMaxWidth(Double.MAX_VALUE);
        initializeTooltip();
        initializeNameLabel();

        for (var i = numberOfBits - 1; i >= 0; i--) {
            var cell = new BitPointerCell(i, readOnly);

            cells[i] = cell;
            getChildren().add(cell);
        }
    }

    public void setData(IntBox intBox) {
        if (this.intBox != null) {
            this.intBox.onChanged().removeListener(changeListener);
        }

        this.intBox = intBox;
        this.intBox.onChanged().addListener(changeListener);

        for (var cell : cells) {
            cell.setData(intBox);
        }
    }

    public void resetChanged() {
        for (var cell : cells) {
            cell.resetChanged();
        }
    }

    private void initializeTooltip() {
        var headerText = new Text(name);
        headerText.getStyleClass().add("text-bold");

        tooltip.setGraphic(headerText);
        tooltip.setContentDisplay(ContentDisplay.TOP);
        tooltip.setTextAlignment(TextAlignment.RIGHT);
        tooltip.setShowDuration(Duration.INDEFINITE);

        Tooltip.install(this, tooltip);
        updateTooltipText();
    }

    private void initializeNameLabel() {
        var label = new Label(name);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().addAll("monospaced", "text-bold");

        getChildren().addAll(label, new Separator(Orientation.VERTICAL));
    }

    private void updateTooltipText() {
        var currentValue = intBox == null ? 0 : intBox.get();
        var text = "%sb\n%Xh\n%<dd".formatted(Integer.toBinaryString(currentValue), currentValue);

        Platform.runLater(() -> tooltip.setText(text));
    }
}
