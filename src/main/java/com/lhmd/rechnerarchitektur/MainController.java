package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.Common.StringUtils;
import com.lhmd.rechnerarchitektur.Instructions.Instruction;
import com.lhmd.rechnerarchitektur.Instructions.LstParser;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGLoader;

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

    @FXML
    private TableColumn<Instruction, Void> breakpointColumn;

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

        for (var themeEntry : ThemeManager.getAllThemes().entrySet()) {
            var menuItem = new CheckMenuItem(themeEntry.getKey());
            menuItem.setOnAction(this::onThemeMenuItemAction);

            var isCurrentTheme = themeEntry.getKey().equals(ThemeManager.getCurrentThemeName());
            menuItem.setSelected(isCurrentTheme);

            themeMenu.getItems().add(menuItem);
        }
    }

    private void initializeInstructionsTableView() {
        var breakpointEnabledSvgUrl = getClass().getResource("svgs/breakpoint-enabled.svg");
        var breakpointDisabledSvgUrl = getClass().getResource("svgs/breakpoint-disabled.svg");
        var disableBreakpointSvgUrl = getClass().getResource("svgs/disable-breakpoint.svg");

        // TODO extract to BreakpointCell class
        breakpointColumn.setCellFactory(p -> {
            // SVG nodes have to be loaded separately for every individual cell
            var breakpointEnabledSvg = SVGLoader.load(breakpointEnabledSvgUrl);
            var breakpointDisabledSvg = SVGLoader.load(breakpointDisabledSvgUrl);
            var disableBreakpointSvg = SVGLoader.load(disableBreakpointSvgUrl);

            var cell = new SvgImageCell<Instruction>();

            // TODO check if breakpoint makes sense (no instruction, just comment)

            cell.setOnMouseClicked(e -> {
                var oldImage = cell.getSvgImage();
                var newImage = oldImage == null || oldImage == breakpointDisabledSvg ? disableBreakpointSvg : breakpointDisabledSvg;

                cell.setSvgImage(newImage);

                var isStyleless = StringUtils.isNullOrEmpty(cell.getTableRow().getStyle());
                cell.getTableRow().setStyle(isStyleless ? "-fx-background-color: #40252B" : null);
            });

            cell.setOnMouseEntered(e -> {
                stage.getScene().setCursor(Cursor.HAND);

                var newImage = cell.getSvgImage() == null ? breakpointDisabledSvg : disableBreakpointSvg;
                cell.setSvgImage(newImage);
            });

            cell.setOnMouseExited(e -> {
                stage.getScene().setCursor(Cursor.DEFAULT);

                var newImage = cell.getSvgImage() == disableBreakpointSvg ? breakpointEnabledSvg : null;
                cell.setSvgImage(newImage);
            });

            return cell;
        });
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
            ExceptionHandler.handle(e);
        }

        instructionsTableView.setItems(FXCollections.observableList(instructions));

        Configuration.addRecentFile(file.getPath());
        initializeOpenRecentMenu();
    }
}
