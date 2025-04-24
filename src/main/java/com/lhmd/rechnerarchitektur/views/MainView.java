package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.Cpu;
import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.components.*;
import com.lhmd.rechnerarchitektur.events.MainMenuBarEvent;
import com.lhmd.rechnerarchitektur.instructions.*;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.parsing.*;
import javafx.beans.Observable;
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
        addEventHandler(MainMenuBarEvent.ON_PAUSE, e -> cpu.setPaused(true));
        addEventHandler(MainMenuBarEvent.ON_NEXT, e -> cpu.nextInstruction());
        addEventHandler(MainMenuBarEvent.ON_RESET, e -> cpu.reset());
    }

    public void shutdownCpu() {
        cpu.shutdown();
    }

    private void initializeProgramMemory() {
        var decodedInstructions = instructionsTableView.getItems()
                .stream()
                .filter(InstructionRowModel::isExecutable)
                .collect(Collectors.toMap(InstructionRowModel::getAddress, i -> InstructionDecoder.decode(i.getInstruction())));

        programMemory = new ProgramMemory(decodedInstructions);
    }

    private void initializeDataMemory() {
        dataMemory = new DataMemory();

        dataMemory.programCounter()
                .changedEvent()
                .addListener((o, n) -> instructionsTableView.setNextRow(n));
    }

    private void initializeCpu() {
        cpu = new Cpu(programMemory, dataMemory, new ProgramStack());
        cpu.onBreakpointReached().addListener(mainMenuBar::pause);
    }

    private void onMainMenuBarFileOpened(MainMenuBarEvent<String> e) {
        var parsedInstructions = Runner.getUnchecked(() -> InstructionParser.parseFile(InstructionRowModel.class, e.getData()));

        var observedInstructions = FXCollections.observableList(parsedInstructions, i -> new Observable[]{i.isBreakpointActiveProperty()});
        observedInstructions.addListener(this::onBreakpointToggled);

        instructionsTableView.setItems(observedInstructions);
        mainMenuBar.setRunnable(true);

        initializeProgramMemory();
        initializeDataMemory();
        initializeCpu();
    }

    private void onMainMenuBarRun(MainMenuBarEvent<Void> e) {
        instructionsTableView.setNextRow(dataMemory.programCounter().get());

        if (!cpu.isAlive()) {
            cpu.start();
        }

        cpu.setPaused(false);
    }

    private void onBreakpointToggled(ListChangeListener.Change<? extends InstructionRowModel> c) {
        while (c.next()) {
            if (!c.wasUpdated()) {
                continue;
            }

            for (var i = c.getFrom(); i < c.getTo(); i++) {
                var instruction = c.getList().get(i);
                var address = instruction.getAddress();

                if (instruction.isBreakpointActiveProperty().get()) {
                    cpu.addBreakpoint(address);
                    continue;
                }

                cpu.removeBreakpoint(address);
            }
        }
    }
}
