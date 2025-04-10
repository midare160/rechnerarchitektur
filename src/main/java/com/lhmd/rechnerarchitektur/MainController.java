package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.instructions.Instruction;
import com.lhmd.rechnerarchitektur.instructions.LstParser;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.net.URL;
import java.util.List;

public class MainController {

    private List<Instruction> instructions;

    private Stage stage;

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

    @FXML
    private TableView<Instruction> instructionsTableView;

    @FXML
    private TableColumn<Instruction, URL> breakpointColumn;

    public void setStage(Stage stage) {
        this.stage = stage;
        onStageInitialized();
    }

    public void initialize() {
        initializeOpenRecentMenu();
        initializeThemeMenu();
        initializeInstructionsTableView();

        aboutMenuItem.setText("About " + ProgramInfo.PROGRAM_NAME);
    }

    private void onStageInitialized() {
        initializeRunButtons();
    }

    private void initializeRunButtons() {
        stage.getScene().setOnKeyPressed(e -> {
            var startCombination = KeyCodeCombination.valueOf("F5");
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

    private void initializeOpenRecentMenu() {
        openRecentMenu.getItems().clear();

        for (var filePath : Configuration.getRecentFiles()) {
            var menuItem = new MenuItem(filePath);
            menuItem.setOnAction(e -> openFile(new File(filePath)));

            openRecentMenu.getItems().add(menuItem);
        }
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

    private void initializeInstructionsTableView() {
        instructionsTableView.setRowFactory(p -> new BreakpointRow());
        breakpointColumn.setCellFactory(p -> new BreakpointCell());
    }

    private void onThemeMenuItemAction(ActionEvent e) {
        var menuItem = (CheckMenuItem) e.getSource();

        ThemeManager.setCurrentThemeName(menuItem.getText());
        ThemeManager.applyCurrentStylesheet(stage.getScene());

        for (var item : themeMenu.getItems()) {
            var checkMenuItem = (CheckMenuItem) item;
            checkMenuItem.setSelected(item == menuItem);
        }
    }

    @FXML
    public void onOpenMenuItemAction(ActionEvent e) {
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

    private void openFile(File file) {
        if (!file.exists()) {
            return;
        }

        try {
            instructions = LstParser.parseFile(file.getPath());
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }

        instructionsTableView.setItems(FXCollections.observableList(instructions));

        Configuration.addRecentFile(file.getPath());
        initializeOpenRecentMenu();
    }

    @FXML
    public void onQuitMenuItemAction(ActionEvent e) {
        var eventArgs = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);

        stage.fireEvent(eventArgs);

        if (eventArgs.isConsumed()) {
            stage.close();
        }
    }


    @FXML
    public void onRunButtonAction() { System.out.println("Run Button pressed"); }

    @FXML
    public void onStopButtonAction() {
        System.out.println("Stop Button pressed");
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
