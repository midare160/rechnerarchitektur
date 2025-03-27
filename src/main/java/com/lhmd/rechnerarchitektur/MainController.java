package com.lhmd.rechnerarchitektur;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
    private TableView<Instruction> instructionsTableView;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        initializeOpenRecentMenu();
        initializeThemeMenu();

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

        for (var themeEntry : ThemeManager.getAllThemes().entrySet()) {
            var menuItem = new CheckMenuItem(themeEntry.getKey());
            menuItem.setOnAction(this::onThemeMenuItemAction);

            var isCurrentTheme = themeEntry.getKey().equals(ThemeManager.getCurrentThemeName());
            menuItem.setSelected(isCurrentTheme);

            themeMenu.getItems().add(menuItem);
        }
    }

    private void initializeInstructionsTableView() {

    }

    private void onThemeMenuItemAction(ActionEvent e) {
        var menuItem = (CheckMenuItem) e.getSource();
        ThemeManager.setCurrentThemeName(menuItem.getText());

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
            instructions = LstParser.parseFile(file.getPath());
        } catch (IOException e) {
        }

        instructionsTableView.setItems(FXCollections.observableList(instructions));

        Configuration.addRecentFile(file.getPath());
        initializeOpenRecentMenu();
    }
}
