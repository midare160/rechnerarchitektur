package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.components.tableview.*;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import com.lhmd.rechnerarchitektur.memory.ProgramMemory;
import com.lhmd.rechnerarchitektur.registers.ProgramCounter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.*;
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

    private VirtualFlow<?> virtualFlow;

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
                Platform.runLater(() -> scrollIntoView(rowModel));
            }
        }
    }

    private VirtualFlow<?> getVirtualFlow() {
        var skin = (TableViewSkin<?>) getSkin();

        return skin.getChildren()
                .stream()
                .filter(VirtualFlow.class::isInstance)
                .map(VirtualFlow.class::cast)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Scrolls the table if the index of the passed {@code rowModel} is not visible.
     */
    private void scrollIntoView(InstructionRowModel rowModel) {
        if (virtualFlow == null) {
            virtualFlow = getVirtualFlow();
        }

        var index = getItems().indexOf(rowModel);

        var first = virtualFlow.getFirstVisibleCell() == null ? Double.POSITIVE_INFINITY : virtualFlow.getFirstVisibleCell().getIndex();
        var last = virtualFlow.getLastVisibleCell() == null ? Double.NEGATIVE_INFINITY : virtualFlow.getLastVisibleCell().getIndex();

        if (index < first || index > last) {
            scrollTo(index);
        }
    }
}
