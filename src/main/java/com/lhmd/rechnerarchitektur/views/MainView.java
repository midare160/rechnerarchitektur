package com.lhmd.rechnerarchitektur.views;

import com.lhmd.rechnerarchitektur.Cpu;
import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.components.*;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.instructions.InstructionRowModel;
import com.lhmd.rechnerarchitektur.memory.*;
import com.lhmd.rechnerarchitektur.parsing.*;
import com.lhmd.rechnerarchitektur.registers.*;
import javafx.beans.Observable;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MainView {
    @FXML
    private VBox root;

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

    private final Cpu cpu;
    private final ProgramCounter programCounter;
    private final InstructionDecoder instructionDecoder;
    private final BeanFactory beanFactory;
    private final ApplicationEventPublisher eventPublisher;

    public MainView(
            Cpu cpu,
            ProgramCounter programCounter,
            InstructionDecoder instructionDecoder,
            BeanFactory beanFactory,
            ApplicationEventPublisher eventPublisher) {
        this.cpu = cpu;
        this.programCounter = programCounter;
        this.instructionDecoder = instructionDecoder;
        this.beanFactory = beanFactory;
        this.eventPublisher = eventPublisher;
    }

    @EventListener(ResetEvent.class)
    public void handleReset() {
        resetChanged();
    }

    @FXML
    private void initialize() {
        initializeData();
        initializeEvents();
    }

    private void initializeData() {
        instructionsTableView.initialize(beanFactory);
        registerTable.initialize(beanFactory);
        stackTable.initialize(beanFactory);
        mainFooter.initialize(beanFactory);
    }

    private void initializeEvents() {
        root.addEventHandler(MainMenuBarEvent.ON_FILE_OPENED, this::onMainMenuBarFileOpened);
        root.addEventHandler(MainMenuBarEvent.ON_FILE_CLOSED, e -> closeCurrentFile());
        root.addEventHandler(MainMenuBarEvent.ON_RUN, this::onMainMenuBarRun);
        root.addEventHandler(MainMenuBarEvent.ON_PAUSE, e -> cpu.setPaused(true));
        root.addEventHandler(MainMenuBarEvent.ON_NEXT, e -> cpu.nextInstruction());
        root.addEventHandler(MainMenuBarEvent.ON_RESET, e -> eventPublisher.publishEvent(new ResetEvent(this)));

        cpu.onBreakpointReached().addListener(mainMenuBar::pause);
        cpu.onNextInstruction().addListener(this::resetChanged);
    }

    private void onMainMenuBarFileOpened(MainMenuBarEvent<String> e) {
        var parsedInstructions = Runner.unchecked(() -> InstructionParser.parseFile(InstructionRowModel.class, e.getData()));

        var observedInstructions = FXCollections.observableList(parsedInstructions, i -> new Observable[]{i.isBreakpointActiveProperty()});
        observedInstructions.addListener(this::onBreakpointToggled);

        var decodedInstructions = parsedInstructions.stream()
                .filter(InstructionRowModel::isExecutable)
                .collect(Collectors.toMap(InstructionRowModel::getAddress, i -> instructionDecoder.decode(i.getInstruction())));

        closeCurrentFile();
        cpu.setProgramMemory(new ProgramMemory(decodedInstructions));

        instructionsTableView.setItems(observedInstructions);
        instructionsTableView.setNextRow(programCounter.get());
        mainMenuBar.setRunnable(true);
    }

    private void onMainMenuBarRun(MainMenuBarEvent<Void> e) {
        instructionsTableView.setNextRow(programCounter.get());

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
