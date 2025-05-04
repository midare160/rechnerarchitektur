package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.events.ChangeListener;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.util.Duration;

public class BitPointerRow extends HBox {
    private final BitPointerCell[] cells;
    private final String name;
    private final Tooltip tooltip;
    private final Label nameLabel;

    private final ChangeListener<Integer> changeListener;

    private IntBox intBox;

    public BitPointerRow(@NamedArg("numberOfBits") int numberOfBits, @NamedArg("name") String name) {
        this.cells = new BitPointerCell[numberOfBits];
        this.name = name;
        this.tooltip = initializeTooltip();
        this.nameLabel = initializeNameLabel();
        this.changeListener = (o, n) -> updateTooltipText();

        setMaxWidth(Double.MAX_VALUE);

        for (var i = numberOfBits - 1; i >= 0; i--) {
            var cell = new BitPointerCell(i);

            cells[i] = cell;
            getChildren().add(cell);
        }
    }

    public Label nameLabel() {
        return nameLabel;
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

    private Tooltip initializeTooltip() {
        var headerText = new Text(name);
        headerText.getStyleClass().add("text-bold");

        var tooltip = new Tooltip();
        tooltip.setGraphic(headerText);
        tooltip.setContentDisplay(ContentDisplay.TOP);
        tooltip.setTextAlignment(TextAlignment.RIGHT);
        tooltip.setShowDuration(Duration.INDEFINITE);

        Tooltip.install(this, tooltip);
        updateTooltipText();

        return tooltip;
    }

    private Label initializeNameLabel() {
        var label = new Label(name);
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(Double.MAX_VALUE);
        label.getStyleClass().addAll("monospaced", "text-bold");

        getChildren().addAll(label, new Separator(Orientation.VERTICAL));

        return label;
    }

    private void updateTooltipText() {
        var currentValue = intBox == null ? 0 : intBox.get();
        var text = "%sb\n%Xh\n%<dd".formatted(Integer.toBinaryString(currentValue), currentValue);

        Platform.runLater(() -> tooltip.setText(text));
    }
}
