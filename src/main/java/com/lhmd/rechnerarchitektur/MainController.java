package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.controllers.ToolbarController;
import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import com.lhmd.rechnerarchitektur.tableview.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.*;

import java.net.URL;

public class MainController {

    private ObservableList<InstructionViewModel> instructions;
    private Stage stage;

    @FXML
    private HBox toolbar;

    @FXML
    private ToolbarController toolbarController;

    @FXML
    private TableView<InstructionViewModel> instructionsTableView;

    @FXML
    private TableColumn<InstructionViewModel, URL> breakpointColumn;

    public void setStage(Stage stage) {
        this.stage = stage;
        toolbarController.setStage(stage);
    }

    private void initializeInstructionsTableView() {
        instructionsTableView.setSelectionModel(null);

        instructionsTableView.setRowFactory(p -> new BreakpointRow());
        breakpointColumn.setCellFactory(p -> new BreakpointCell());
    }


}
