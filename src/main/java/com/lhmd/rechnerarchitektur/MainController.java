package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.instructions.InstructionViewModel;
import com.lhmd.rechnerarchitektur.parsing.LstParser;
import com.lhmd.rechnerarchitektur.tableview.*;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.*;
import java.net.URL;

public class MainController {

    private ObservableList<InstructionViewModel> instructions;
    private Stage stage;

    @FXML
    private Menu themeMenu;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private TableView<InstructionViewModel> instructionsTableView;

    @FXML
    private TableColumn<InstructionViewModel, URL> breakpointColumn;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        initializeOpenRecentMenu();
        initializeThemeMenu();
        initializeInstructionsTableView();

        aboutMenuItem.setText("About " + ProgramInfo.PROGRAM_NAME);
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

        for (var themeName : ThemeManager.allThemes().keySet()) {
            var menuItem = new CheckMenuItem(themeName);
            menuItem.setOnAction(this::onThemeMenuItemAction);

            var isCurrentTheme = themeName.equals(ThemeManager.getCurrentThemeName());
            menuItem.setSelected(isCurrentTheme);

            themeMenu.getItems().add(menuItem);
        }
    }

    private void initializeInstructionsTableView() {
        instructionsTableView.setSelectionModel(null);

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
    public void onAboutMenuItemAction(ActionEvent e) {
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
            var parsedInstructions = LstParser.parseFile(file.getPath());
            instructions = FXCollections.observableList(parsedInstructions);
        } catch (IOException e) {
            ExceptionHandler.handle(e);
        }

        instructionsTableView.setItems(instructions);

        Configuration.addRecentFile(file.getPath());
        initializeOpenRecentMenu();
    }

    @FXML
    public void onQuitMenuItemAction(ActionEvent e) {
        var eventArgs = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
        stage.fireEvent(eventArgs);
    }
}
