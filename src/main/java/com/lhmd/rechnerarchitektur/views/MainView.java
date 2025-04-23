package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.events.MainMenuBarEvent;
import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import com.lhmd.rechnerarchitektur.parsing.LstParser;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MainView extends VBox {
    @FXML
    private TableView<InstructionViewModel> instructionsTableView;

    private ObservableList<InstructionViewModel> instructions;

    public MainView() {
        FxUtils.loadHierarchy(this, "views/main.fxml");
    }

    @FXML
    public void initialize() {
        addEventHandler(MainMenuBarEvent.ON_FILE_OPENED, e -> {
            var parsedInstructions = Runner.getUnchecked(() -> LstParser.parseFile(e.getData()));
            instructions = FXCollections.observableList(parsedInstructions);

            instructionsTableView.setItems(instructions);
        });
    }
}
