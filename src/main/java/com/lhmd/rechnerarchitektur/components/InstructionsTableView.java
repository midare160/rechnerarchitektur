package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import com.lhmd.rechnerarchitektur.tableview.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.BeanFactory;

import java.net.URL;
import java.util.Objects;

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

    public void initialize(BeanFactory beanFactory) {
        var programCounter = beanFactory.getBean(ProgramCounter.class);
        programCounter.onChanged().addListener((o, n) -> setNextRow(n));

        setRowFactory(p -> new InstructionTableRow());

        breakpointColumn.setCellFactory(p -> new BreakpointTableCell());
        addressColumn.setCellFactory(p -> new FormattedTableCell<>("%04X"::formatted));
        instructionColumn.setCellFactory(p -> new FormattedTableCell<>("%04X"::formatted));
        lineNumberColumn.setCellFactory(p -> new FormattedTableCell<>("%05d"::formatted));
    }

    public void setNextRow(Integer address) {
        for (var rowModel : getItems()) {
            var isNext = Objects.equals(address % ProgramMemory.MAX_SIZE, rowModel.getAddress());
            rowModel.isNextProperty().set(isNext);

            if (isNext) {
                Platform.runLater(() -> scrollTo(rowModel));
            }
        }
    }
}
