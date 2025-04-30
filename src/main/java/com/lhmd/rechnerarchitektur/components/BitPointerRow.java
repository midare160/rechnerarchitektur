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
    private final List<BitPointerCell> cells;
    private final String name;
    private final Tooltip tooltip;
    private final ChangeListener<Integer> changeListener;

    private IntBox intBox;

    public BitPointerRow(@NamedArg("numberOfBits") int numberOfBits, @NamedArg("name") String name, @NamedArg("readOnly") boolean readOnly) {
        this.cells = new ArrayList<>(numberOfBits);
        this.name = name;
        this.tooltip = new Tooltip();
        this.changeListener = (o, n) -> updateTooltipText();

        setMaxWidth(Double.MAX_VALUE);
        addChildLabel(new Label(name));

        for (var i = numberOfBits - 1; i >= 0; i--) {
            var cell = new BitPointerCell(i, readOnly);
            cells.add(cell);
            addChildLabel(cell);
        }

        Tooltip.install(this, tooltip);
        updateTooltipText();
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

    private void addChildLabel(Label label) {
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.getStyleClass().add("monospaced");

        HBox.setHgrow(label, Priority.ALWAYS);

        getChildren().add(label);
    }

    private void updateTooltipText() {
        final var text = "%xh\n%dd";

        var currentValue = intBox == null ? 0 : intBox.get();
        var formatted = text.formatted(currentValue, currentValue);

        Platform.runLater(() -> tooltip.setText(formatted));
    }
}
