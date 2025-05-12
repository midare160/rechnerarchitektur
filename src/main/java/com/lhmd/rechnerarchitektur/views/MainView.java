package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.Cpu;
import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.components.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
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

    @FXML
    private RegisterTable registerTable;

    @FXML
    private StackTable stackTable;

    @FXML
    private MainFooter mainFooter;

    private final DataMemory dataMemory;
    private final ProgramStack programStack;
    private final Cpu cpu;

    public MainView() {
        this.dataMemory = new DataMemory();
        this.programStack = new ProgramStack();
        this.cpu = new Cpu(dataMemory, programStack);

        FxUtils.loadHierarchy(this, "views/main.fxml");
    }

    public void shutdownCpu() {
        if (cpu != null) {
            cpu.shutdown();
        }
    }

    @FXML
    private void initialize() {
        initializeData();
        initializeEvents();
    }

    private void initializeData() {
        registerTable.setData(dataMemory);
        stackTable.setData(programStack);
        mainFooter.setData(dataMemory.W(), dataMemory.programCounter());
    }

    private void initializeEvents() {
        addEventHandler(MainMenuBarEvent.ON_FILE_OPENED, this::onMainMenuBarFileOpened);
        addEventHandler(MainMenuBarEvent.ON_FILE_CLOSED, e -> closeCurrentFile());
        addEventHandler(MainMenuBarEvent.ON_RUN, this::onMainMenuBarRun);
        addEventHandler(MainMenuBarEvent.ON_PAUSE, e -> cpu.setPaused(true));
        addEventHandler(MainMenuBarEvent.ON_NEXT, e -> cpu.nextInstruction());
        addEventHandler(MainMenuBarEvent.ON_RESET, this::onMainMenuBarReset);

        dataMemory.programCounter().onChanged().addListener((o, n) -> instructionsTableView.setNextRow(n));

        cpu.onBreakpointReached().addListener(mainMenuBar::pause);
        cpu.onNextInstruction().addListener(this::resetChanged);
    }

    private void onMainMenuBarFileOpened(MainMenuBarEvent<String> e) {
        var parsedInstructions = Runner.unchecked(() -> InstructionParser.parseFile(InstructionRowModel.class, e.getData()));

        var observedInstructions = FXCollections.observableList(parsedInstructions, i -> new Observable[]{i.isBreakpointActiveProperty()});
        observedInstructions.addListener(this::onBreakpointToggled);

        var decodedInstructions = parsedInstructions.stream()
                .filter(InstructionRowModel::isExecutable)
                .collect(Collectors.toMap(InstructionRowModel::getAddress, i -> InstructionDecoder.decode(i.getInstruction())));

        closeCurrentFile();
        cpu.setProgramMemory(new ProgramMemory(decodedInstructions));

        instructionsTableView.setItems(observedInstructions);
        instructionsTableView.setNextRow(dataMemory.programCounter().get());
        mainMenuBar.setRunnable(true);
    }

    private void onMainMenuBarRun(MainMenuBarEvent<Void> e) {
        instructionsTableView.setNextRow(dataMemory.programCounter().get());

        if (!cpu.isAlive()) {
            cpu.start();
        }

        cpu.setPaused(false);
    }

    private void onMainMenuBarReset(MainMenuBarEvent<Void> e) {
        cpu.reset();
        resetChanged();
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

    private void closeCurrentFile() {
        mainMenuBar.setRunnable(false);
        instructionsTableView.setItems(FXCollections.emptyObservableList());

        cpu.setPaused(true);
        cpu.setProgramMemory(null);
        cpu.clearBreakpoints();
    }

    private void resetChanged() {
        registerTable.resetChanged();
        stackTable.resetChanged();
        mainFooter.resetChanged();
    }
}
