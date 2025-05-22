package com.lhmd.rechnerarchitektur.components;

import com.lhmd.rechnerarchitektur.*;
import com.lhmd.rechnerarchitektur.common.*;
import com.lhmd.rechnerarchitektur.configuration.*;
import com.lhmd.rechnerarchitektur.events.MainMenuBarEvent;
import com.lhmd.rechnerarchitektur.styles.ThemeManager;
import com.lhmd.rechnerarchitektur.views.Preferences;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import org.girod.javafx.svgimage.SVGLoader;
import org.springframework.beans.factory.*;

import java.io.File;

public class MainMenuBar extends HBox {
    private static final KeyCombination REOPEN_COMBINATION = KeyCombination.valueOf("SHORTCUT+SHIFT+T");

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private Menu openRecentMenu;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem preferencesMenuItem;

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

    private ObjectProvider<Preferences> preferencesProvider;
    private UserConfig userConfig;
    private ThemeManager themeManager;

    public MainMenuBar() {
        FxUtils.loadHierarchy(this, "components/mainMenuBar.fxml");
    }

    public void initialize(BeanFactory beanFactory) {
        preferencesProvider = beanFactory.getBeanProvider(Preferences.class);
        userConfig = beanFactory.getBean(UserConfigService.class).config();
        themeManager = beanFactory.getBean(ThemeManager.class);

        initializeEvents();
        initializeOpenRecentMenu();
        initializeThemeMenu();
        initializeActionMenuBar();

        aboutMenuItem.setText("About " + ProgramInfo.PROGRAM_NAME);
    }

    public void setRunnable(boolean runnable) {
        runMenu.setDisable(!runnable);
        nextMenu.setDisable(!runnable);

        if (!runnable) {
            pauseMenu.setDisable(true);
        }
    }

    public void pause() {
        runMenu.setDisable(false);
        pauseMenu.setDisable(true);
        nextMenu.setDisable(false);
    }

    private void initializeEvents() {
        openMenuItem.setOnAction(this::onOpenMenuItemAction);
        closeMenuItem.setOnAction(this::onCloseMenuItemAction);
        preferencesMenuItem.setOnAction(this::onPreferencesMenuItemAction);
        quitMenuItem.setOnAction(this::onQuitMenuItemAction);
        aboutMenuItem.setOnAction(this::onAboutMenuItemAction);

        runMenu.setOnAction(this::onRunMenuAction);
        nextMenu.setOnAction(this::onNextMenuAction);
        pauseMenu.setOnAction(this::onPauseMenuAction);
        resetMenu.setOnAction(this::onResetMenuAction);
    }

    private void initializeOpenRecentMenu() {
        openRecentMenu.getItems().clear();

        for (var filePath : userConfig.getFileHistory()) {
            var menuItem = new MenuItem(filePath);
            menuItem.setOnAction(e -> openFile(new File(filePath)));

            openRecentMenu.getItems().add(menuItem);
        }

        setReopenAccelerator();
    }

    private void initializeThemeMenu() {
        var shortcutIndex = 1;

        for (var themeName : ThemeManager.allThemes().keySet()) {
            var menuItem = new CheckMenuItem(themeName);
            menuItem.setOnAction(this::onThemeMenuItemAction);
            menuItem.setAccelerator(KeyCombination.valueOf("SHORTCUT+" + shortcutIndex++));

            var isCurrentTheme = themeName.equals(themeManager.getCurrentThemeName());
            menuItem.setSelected(isCurrentTheme);

            themeMenu.getItems().add(menuItem);
        }
    }

    private void initializeActionMenuBar() {
        runMenu.setGraphic(SVGLoader.load(JavaFxApplication.class.getResource("svgs/run.svg")));
        pauseMenu.setGraphic(SVGLoader.load(JavaFxApplication.class.getResource("svgs/pause.svg")));
        nextMenu.setGraphic(SVGLoader.load(JavaFxApplication.class.getResource("svgs/step.svg")));
        resetMenu.setGraphic(SVGLoader.load(JavaFxApplication.class.getResource("svgs/reset.svg")));

        for (var menu : actionsMenuBar.getMenus()) {
            FxUtils.asMenuItem(menu);
        }
    }

    private void setReopenAccelerator() {
        if (getScene() != null) {
            getScene().getAccelerators().remove(REOPEN_COMBINATION);
        }

        if (openRecentMenu.getItems().isEmpty()) {
            return;
        }

        var firstItem = openRecentMenu.getItems().getFirst();
        firstItem.setAccelerator(REOPEN_COMBINATION);

        if (getScene() != null) {
            getScene().getAccelerators().put(REOPEN_COMBINATION, firstItem::fire);
        }
    }

    private void openFile(File file) {
        if (!file.exists()) {
            return;
        }

        var filePath = file.getPath();

        // The most recent file should always be on top
        userConfig.getFileHistory().removeIf(f -> f.equals(filePath));
        userConfig.getFileHistory().addFirst(filePath);

        initializeOpenRecentMenu();

        fireEvent(new MainMenuBarEvent<>(MainMenuBarEvent.ON_FILE_OPENED, filePath));
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

    private void onPreferencesMenuItemAction(ActionEvent e) {
        var loader = new FXMLLoader(JavaFxApplication.class.getResource("views/preferences.fxml"));
        loader.setControllerFactory(c -> preferencesProvider.getObject());

        var scene = new Scene(Runner.unchecked(() -> loader.load()));
        themeManager.registerScene(scene);

        var stage = new Stage();
        stage.initOwner(getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Preferences");

        stage.show();
        FxUtils.centerStage(stage);
    }

    private void onQuitMenuItemAction(ActionEvent e) {
        FxUtils.closeWindow(getScene().getWindow());
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

        themeManager.setCurrentThemeName(menuItem.getText());

        for (var item : themeMenu.getItems()) {
            var checkMenuItem = (CheckMenuItem) item;
            checkMenuItem.setSelected(item == menuItem);
        }
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
