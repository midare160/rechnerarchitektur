package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import com.lhmd.rechnerarchitektur.tableview.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;

public class InstructionsTableView extends TableView<InstructionRowModel> {
    @FXML
    private TableColumn<InstructionRowModel, URL> breakpointColumn;

    @FXML
    private TableColumn<InstructionRowModel, Integer> addressColumn;

    @FXML
    private TableColumn<InstructionRowModel, Integer> instructionColumn;

    @FXML
    private TableColumn<InstructionRowModel, Integer> lineNumberColumn;

    public InstructionsTableView() {
        FxUtils.loadHierarchy(this, "components/instructionsTableView.fxml");
    }

    @FXML
    public void initialize() {
        setRowFactory(p -> new BreakpointTableRow());

        breakpointColumn.setCellFactory(p -> new BreakpointTableCell());
        addressColumn.setCellFactory(p -> new FormattedTableCell<>("%04X"::formatted));
        instructionColumn.setCellFactory(p -> new FormattedTableCell<>("%04X"::formatted));
        lineNumberColumn.setCellFactory(p -> new FormattedTableCell<>("%05d"::formatted));
    }
}
