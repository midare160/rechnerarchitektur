package com.lhmd.rechnerarchitektur.controllers;
import com.lhmd.rechnerarchitektur.Configuration;
import com.lhmd.rechnerarchitektur.ExceptionHandler;
import com.lhmd.rechnerarchitektur.Launcher;
import com.lhmd.rechnerarchitektur.ProgramInfo;
import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import com.lhmd.rechnerarchitektur.parsing.LstParser;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.input.KeyCodeCombination;
import org.girod.javafx.svgimage.SVGLoader;
import javafx.stage.*;

import javafx.fxml.FXML;
import javafx.stage.*;

import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import java.io.File;
import java.io.IOException;

public class ToolbarController {
    private ObservableList<InstructionViewModel> instructions;

    @FXML
    private Menu themeMenu;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private Button runButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button stepButton;

    @FXML
    private Button pauseButton;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        onStageInitialized();
    }

    public void initialize() {
        initializeOpenRecentMenu();
        initializeThemeMenu();
        //initializeInstructionsTableView();
        loadSvgIcon();

        aboutMenuItem.setText("About " + ProgramInfo.PROGRAM_NAME);
    }

    private void openFile(File file) {
        if (!file.exists()) {
            return;
        }

        try {
            var parsedInstructions = LstParser.parseFile(file.getPath());
            instructions = FXCollections.observableList(parsedInstructions);
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }

        //instructionsTableView.setItems(instructions);

        Configuration.addRecentFile(file.getPath());
        initializeOpenRecentMenu();
    }

    private void initializeOpenRecentMenu() {
        openRecentMenu.getItems().clear();

        for (var filePath : Configuration.getRecentFiles()) {
            var menuItem = new MenuItem(filePath);
            menuItem.setOnAction(e -> openFile(new File(filePath)));

            openRecentMenu.getItems().add(menuItem);
        }
    }

    @FXML
    public void onQuitMenuItemAction(ActionEvent e) {
        var eventArgs = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
        stage.fireEvent(eventArgs);
    }

    private void initializeThemeMenu() {
        themeMenu.getItems().clear();

        for (var themeName : ThemeManager.getAllThemes().keySet()) {
            var menuItem = new CheckMenuItem(themeName);
            menuItem.setOnAction(this::onThemeMenuItemAction);

            var isCurrentTheme = themeName.equals(ThemeManager.getCurrentThemeName());
            menuItem.setSelected(isCurrentTheme);

            themeMenu.getItems().add(menuItem);
        }
    }

    private void onThemeMenuItemAction(javafx.event.ActionEvent e) {
        var menuItem = (CheckMenuItem) e.getSource();

        ThemeManager.setCurrentThemeName(menuItem.getText());
        ThemeManager.applyCurrentStylesheet(stage.getScene());

        for (var item : themeMenu.getItems()) {
            var checkMenuItem = (CheckMenuItem) item;
            checkMenuItem.setSelected(item == menuItem);
        }
    }

    @FXML
    public void onOpenMenuItemAction(javafx.event.ActionEvent e) {
        var fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("List Files", "*.lst", "*.LST"));

        var selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) {
            return;
        }

        openFile(selectedFile);
    }

    @FXML
    public void onAboutMenuItemAction() {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle("About " + ProgramInfo.PROGRAM_NAME);
        alert.setHeaderText(alert.getTitle());

        alert.setContentText("""
                © 2025
                
                Laurin Hauff
                Michael Daubert
                """);

        alert.showAndWait();
    }
    private void loadSvgIcon() {
        var runButtonSvgUrl = Launcher.class.getResource("svgs/run.svg");
        var stopButtonSvgUrl = Launcher.class.getResource("svgs/stop.svg");
        var pauseButtonSvgUrl = Launcher.class.getResource("svgs/pause.svg");
        runButton.setGraphic(SVGLoader.load(runButtonSvgUrl));
        stopButton.setGraphic(SVGLoader.load(stopButtonSvgUrl));
        pauseButton.setGraphic(SVGLoader.load(pauseButtonSvgUrl));
        stepButton.setGraphic(SVGLoader.load(runButtonSvgUrl));
    }

    private void onStageInitialized() {
        initializeRunButtons();
        runButton.setVisible(true);
        runButton.setManaged(true);
        stopButton.setVisible(false);
        stopButton.setManaged(false);
    }

    private void initializeRunButtons() {
        stage.getScene().setOnKeyPressed(e -> {
            var startCombination = KeyCodeCombination.valueOf("F5");
            //var stopCombination = new KeyCodeCombination(KeyCode.F5, KeyCodeCombination.SHIFT_ANY);
            var stopCombination = KeyCodeCombination.valueOf("SHIFT+F5");
            var stepCombination = KeyCodeCombination.valueOf("F10");
            var pauseCombination = KeyCodeCombination.valueOf("CTRL+ALT+B");
            if (startCombination.match(e)) {
                onRunButtonAction();
            } else if (stopCombination.match(e)) {
                onStopButtonAction();
            } else if (stepCombination.match(e)) {
                onStepButtonAction();
            } else if (pauseCombination.match(e)) {
                onResetButtonAction();
            }
        });
    }

    @FXML
    public void onRunButtonAction() {
        System.out.println("Run Button pressed");
        runButton.setVisible(false);
        runButton.setManaged(false);
        stopButton.setVisible(true);
        stopButton.setManaged(true);
    }

    @FXML
    public void onStopButtonAction() {
        System.out.println("Stop Button pressed");
        runButton.setVisible(true);
        runButton.setManaged(true);
        stopButton.setVisible(false);
        stopButton.setManaged(false);
    }

    @FXML
    public void onStepButtonAction() {
        System.out.println("Step Button pressed");
    }

    @FXML
    public void onResetButtonAction() {
        System.out.println("Reset Button pressed");
    }
}