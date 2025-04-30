package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.events.ChangeListener;
import com.lhmd.rechnerarchitektur.values.IntBox;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;

public class BitPointerRow extends HBox {
    private final BitPointerCell[] cells;
    private final String name;
    private final Text tooltipText;
    private final ChangeListener<Integer> changeListener;

    private IntBox intBox;

    public BitPointerRow(@NamedArg("numberOfBits") int numberOfBits, @NamedArg("name") String name, @NamedArg("readOnly") boolean readOnly) {
        this.cells = new BitPointerCell[numberOfBits];
        this.name = name;
        this.tooltipText = new Text(name);
        this.changeListener = (o, n) -> updateTooltipText();

        setMaxWidth(Double.MAX_VALUE);
        initializeTooltip();

        // TODO don't resize like cell labels
        addChildLabel(new Label(name));

        for (var i = numberOfBits - 1; i >= 0; i--) {
            var cell = new BitPointerCell(i, readOnly);
            cells[i] = cell;
            addChildLabel(cell);
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
            cell.setChanged(false);
        }
    }

    private void initializeTooltip() {
        var headerText = new Text(name);
        headerText.getStyleClass().add("text-bold");

        var textFlow = new TextFlow(headerText, tooltipText);

        var tooltip = new Tooltip();
        tooltip.setGraphic(textFlow);
        tooltip.setWrapText(true);

        Tooltip.install(this, tooltip);
        updateTooltipText();
    }

    private void addChildLabel(Label label) {
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("monospaced");

        HBox.setHgrow(label, Priority.ALWAYS);

        getChildren().add(label);
    }

    private void updateTooltipText() {
        var currentValue = intBox == null ? 0 : intBox.get();
        var text = "\n%Xh\n%dd".formatted(currentValue, currentValue);

        Platform.runLater(() -> tooltipText.setText(text));
    }
}
