package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.Cpu;
import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.components.*;
import com.lhmd.rechnerarchitektur.events.MainMenuBarEvent;
import com.lhmd.rechnerarchitektur.instructions.*;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.parsing.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.stream.Collectors;

public class MainView extends VBox {
    @FXML
    private MainMenuBar mainMenuBar;

    @FXML
    private InstructionsTableView instructionsTableView;

    private ProgramMemory programMemory;
    private DataMemory dataMemory;
    private Cpu cpu;

    public MainView() {
        FxUtils.loadHierarchy(this, "views/main.fxml");
    }

    @FXML
    public void initialize() {
        addEventHandler(MainMenuBarEvent.ON_FILE_OPENED, this::onMainMenuBarFileOpened);
        addEventHandler(MainMenuBarEvent.ON_RUN, this::onMainMenuBarRun);
        addEventHandler(MainMenuBarEvent.ON_PAUSE, e -> cpu.pause());
        addEventHandler(MainMenuBarEvent.ON_NEXT, e -> cpu.next());
        addEventHandler(MainMenuBarEvent.ON_RESET, e -> cpu.reset());
    }

    private void initializeProgramMemory() {
        var decodedInstructions = instructionsTableView.getItems()
                .stream()
                .filter(i -> i.getAddress() != null)
                .collect(Collectors.toMap(InstructionRowModel::getAddress, i -> InstructionDecoder.decode(i.getInstruction())));

        programMemory = new ProgramMemory(decodedInstructions);
    }

    private void initializeDataMemory() {
        dataMemory = new DataMemory();
        dataMemory.programCounter().changedEvent().addListener((o, n) -> instructionsTableView.setNextRow(n));
    }

    private void initializeCpu() {
        cpu = new Cpu(programMemory, dataMemory, new ProgramStack());
    }

    private void onMainMenuBarFileOpened(MainMenuBarEvent<String> e) {
        var parsedInstructions = Runner.getUnchecked(() -> InstructionParser.parseFile(InstructionRowModel.class, e.getData()));

        instructionsTableView.setItems(FXCollections.observableList(parsedInstructions));
        mainMenuBar.setRunAllowed(true);

        initializeProgramMemory();
        initializeDataMemory();
        initializeCpu();
    }

    private void onMainMenuBarRun(MainMenuBarEvent<Void> e) {
        instructionsTableView.setNextRow(dataMemory.programCounter().get());
        cpu.run();
    }
}
