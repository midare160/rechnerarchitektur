package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.Configuration;
import com.lhmd.rechnerarchitektur.Launcher;
import com.lhmd.rechnerarchitektur.ProgramInfo;
import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.events.MainMenuBarEvent;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.WindowEvent;
import org.girod.javafx.svgimage.SVGLoader;

import java.io.File;

public class MainMenuBar extends HBox {
    @FXML
    private MenuItem openMenuItem;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem quitMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Menu themeMenu;

    @FXML
    private MenuBar actionsMenuBar;

    @FXML
    private Menu runMenu;

    @FXML
    private Menu pauseMenu;

    @FXML
    private Menu nextMenu;

    @FXML
    private Menu resetMenu;

    public MainMenuBar() {
        FxUtils.loadHierarchy(this, "components/mainMenuBar.fxml");
    }

    public void setRunnable(boolean runnable) {
        runMenu.setDisable(!runnable);

        if (!runnable) {
            pauseMenu.setDisable(true);
            nextMenu.setDisable(true);
        }
    }

    public void pause() {
        runMenu.setDisable(false);
        pauseMenu.setDisable(true);
        nextMenu.setDisable(false);
    }

    @FXML
    private void initialize() {
        initializeEvents();
        initializeOpenRecentMenu();
        initializeThemeMenu();
        initializeActionMenuBar();

        aboutMenuItem.setText("About " + ProgramInfo.PROGRAM_NAME);
    }

    private void initializeEvents() {
        openMenuItem.setOnAction(this::onOpenMenuItemAction);
        closeMenuItem.setOnAction(this::onCloseMenuItemAction);
        quitMenuItem.setOnAction(this::onQuitMenuItemAction);
        aboutMenuItem.setOnAction(this::onAboutMenuItemAction);

        runMenu.setOnAction(this::onRunMenuAction);
        nextMenu.setOnAction(this::onNextMenuAction);
        pauseMenu.setOnAction(this::onPauseMenuAction);
        resetMenu.setOnAction(this::onResetMenuAction);
    }

    private void initializeOpenRecentMenu() {
        var items = openRecentMenu.getItems();
        items.clear();

        for (var filePath : Configuration.getRecentFiles()) {
            var menuItem = new MenuItem(filePath);
            menuItem.setOnAction(e -> openFile(new File(filePath)));

            items.add(menuItem);
        }

        items.getFirst().setAccelerator(KeyCombination.valueOf("SHORTCUT+SHIFT+T"));
    }

    private void initializeThemeMenu() {
        themeMenu.getItems().clear();

        var shortcutIndex = 1;

        for (var themeName : ThemeManager.allThemes().keySet()) {
            var menuItem = new CheckMenuItem(themeName);
            menuItem.setOnAction(this::onThemeMenuItemAction);
            menuItem.setAccelerator(KeyCombination.valueOf("SHORTCUT+" + shortcutIndex++));

            var isCurrentTheme = themeName.equals(ThemeManager.getCurrentThemeName());
            menuItem.setSelected(isCurrentTheme);

            themeMenu.getItems().add(menuItem);
        }
    }

    private void initializeActionMenuBar() {
        runMenu.setGraphic(SVGLoader.load(Launcher.class.getResource("svgs/run.svg")));
        pauseMenu.setGraphic(SVGLoader.load(Launcher.class.getResource("svgs/pause.svg")));
        nextMenu.setGraphic(SVGLoader.load(Launcher.class.getResource("svgs/run.svg")));
        resetMenu.setGraphic(SVGLoader.load(Launcher.class.getResource("svgs/reset.svg")));

        for (var menu : actionsMenuBar.getMenus()) {
            FxUtils.asMenuItem(menu);
        }
    }

    private void onOpenMenuItemAction(ActionEvent e) {
        var fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("List Files", "*.lst", "*.LST"));

        var selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

        if (selectedFile == null) {
            return;
        }

        openFile(selectedFile);
    }

    private void onCloseMenuItemAction(ActionEvent e) {
        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_FILE_CLOSED));
    }

    private void onQuitMenuItemAction(ActionEvent e) {
        var event = new WindowEvent(getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST);
        getScene().getWindow().fireEvent(event);
    }

    private void onAboutMenuItemAction(ActionEvent e) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(getScene().getWindow());
        alert.setTitle("About " + ProgramInfo.PROGRAM_NAME);
        alert.setHeaderText(alert.getTitle());

        alert.setContentText("""
                © 2025
                
                Laurin Hauff
                Michael Daubert
                """);

        alert.showAndWait();
    }

    private void onThemeMenuItemAction(ActionEvent e) {
        var menuItem = (CheckMenuItem) e.getSource();

        ThemeManager.setCurrentThemeName(menuItem.getText());
        ThemeManager.applyCurrentStylesheet(getScene());

        for (var item : themeMenu.getItems()) {
            var checkMenuItem = (CheckMenuItem) item;
            checkMenuItem.setSelected(item == menuItem);
        }
    }

    private void openFile(File file) {
        if (!file.exists()) {
            return;
        }

        Configuration.addRecentFile(file.getPath());
        initializeOpenRecentMenu();

        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_FILE_OPENED, file.getPath()));
    }

    private void onRunMenuAction(ActionEvent e) {
        runMenu.setDisable(true);
        nextMenu.setDisable(true);
        pauseMenu.setDisable(false);

        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_RUN));
    }

    private void onResetMenuAction(ActionEvent e) {
        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_RESET));
    }

    private void onPauseMenuAction(ActionEvent e) {
        pause();
        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_PAUSE));
    }

    private void onNextMenuAction(ActionEvent e) {
        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_NEXT));
    }
}
