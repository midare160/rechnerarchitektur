package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.Cpu;
import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.events.MainMenuBarEvent;
import com.lhmd.rechnerarchitektur.instructions.*;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.parsing.InstructionDecoder;
import com.lhmd.rechnerarchitektur.parsing.InstructionParser;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class MainView extends VBox {
    @FXML
    private TableView<InstructionRowModel> instructionsTableView;

    private ObservableList<InstructionRowModel> instructions;

    public MainView() {
        FxUtils.loadHierarchy(this, "views/main.fxml");
    }

    @FXML
    public void initialize() {
        initializeEvents();
    }

    private void initializeEvents() {
        // TODO use observer pattern instead of global events
        addEventHandler(MainMenuBarEvent.ON_FILE_OPENED, this::onMainMenuBarFileOpened);
        addEventHandler(MainMenuBarEvent.ON_RUN, this::onMainMenuBarRun);
    }

    private void onMainMenuBarFileOpened(MainMenuBarEvent<String> e) {
        var parsedInstructions = Runner.getUnchecked(() -> InstructionParser.parseFile(InstructionRowModel.class, e.getData()));
        instructions = FXCollections.observableList(parsedInstructions);

        instructionsTableView.setItems(instructions);
    }

    // TODO make it cleaner, extra methods for programmemory and datamemory? + hashmap for instructions
    private void onMainMenuBarRun(MainMenuBarEvent<Void> e) {
        var decodedInstructions = instructions.stream()
                .filter(i -> i.getInstruction() != null)
                .collect(Collectors.toMap(InstructionRowModel::getAddress, i -> InstructionDecoder.decode(i.getInstruction())));

        var memory = new DataMemory();

        // TODO create map<address, InstructionRowModel> for all instructions that have getAddress != null
        memory.programCounter().changedEvent().addListener((o, n) -> {
            var currentRow = instructions.stream().filter(i -> o.equals(i.getAddress())).findFirst().orElse(null);
            if (currentRow != null) {
                // TODO set css pseudo class instead of selecting for highlighting next row
                instructionsTableView.getSelectionModel().select(currentRow);
            }
        });

        var cpu = new Cpu(
                new ProgramMemory(decodedInstructions),
                memory,
                new ProgramStack()
        );

        cpu.run();
    }
}
