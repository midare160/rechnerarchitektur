package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.common.IntUtils;
import com.lhmd.rechnerarchitektur.models.BitPointerSet;
import com.lhmd.rechnerarchitektur.tableview.BitTableCell;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class BitsTableView extends TableView<BitPointerSet> {
    private final int numberOfBits;

    public BitsTableView(@NamedArg("numberOfBits") int numberOfBits) {
        this.numberOfBits = IntUtils.requireLargerZero(numberOfBits);
        FxUtils.loadHierarchy(this, "components/bitsTableView.fxml");
    }

    public void resetChangedCells() {
        for (var node : lookupAll(".table-cell:changed")) {
            if (node instanceof BitTableCell<?> cell) {
                cell.resetChanged();
            }
        }
    }

    @FXML
    private void initialize() {
        getSelectionModel().setCellSelectionEnabled(true);
        initializeBitColumns();
    }

    private void initializeBitColumns() {
        for (var i = numberOfBits - 1; i >= 0; i--) {
            // Java copies values as references into lambdas, so we have to declare an additional readonly variable
            final var finalI = i;

            var column = new TableColumn<BitPointerSet, Boolean>(Integer.toString(i));
            column.setCellFactory(p -> new BitTableCell<>());
            column.setCellValueFactory(p -> p.getValue().get(finalI));
            column.getStyleClass().add("align-center");

            getColumns().add(column);
        }
    }
}
