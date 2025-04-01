package com.lhmd.rechnerarchitektur;

import javafx.scene.control.TableRow;

import java.util.function.Predicate;

public class BreakpointRow<S> extends TableRow<S> {
    private final Predicate<S> isHighlighted;

    public BreakpointRow(Predicate<S> isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    @Override
    protected void updateItem(S item, boolean empty) {
        super.updateItem(item, empty);

        final var activeBreakpointClass = "table-row-breakpoint";

        if (empty || item == null || !isHighlighted.test(item)) {
            setStyle("");
            setGraphic(null);
            return;
        }

        setStyle("-fx-background-color: #40252b;");
    }
}
