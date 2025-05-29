package com.lhmd.rechnerarchitektur.components.tableview;

import javafx.scene.control.TableCell;

import java.util.function.Function;

public class FormattedTableCell<S, T> extends TableCell<S, T> {
    private final Function<T, String> formatValue;

    public FormattedTableCell(Function<T, String> formatValue) {
        this.formatValue = formatValue;
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        setText(formatValue.apply(item));
    }
}
