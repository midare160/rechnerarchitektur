package com.lhmd.rechnerarchitektur.common;

import com.lhmd.rechnerarchitektur.JavaFxApplication;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.*;

public final class FxUtils {
    private FxUtils() {
    }

    public static <T> void loadHierarchy(T controller, String fxmlPath) {
        var loader = new FXMLLoader(JavaFxApplication.class.getResource(fxmlPath));
        loader.setRoot(controller);
        loader.setController(controller);

        Runner.unchecked(() -> loader.load());
    }

    @FXML
    public static void setAllAnchors(Node child, Double value) {
        AnchorPane.setTopAnchor(child, value);
        AnchorPane.setRightAnchor(child, value);
        AnchorPane.setBottomAnchor(child, value);
        AnchorPane.setLeftAnchor(child, value);
    }

    @FXML
    public static Double getAllAnchors(Node child) {
        return AnchorPane.getTopAnchor(child);
    }

    /**
     * Simulates the behavior of a MenuItem for a Menu.
     */
    public static void asMenuItem(Menu menu) {
        var dummyItem = new MenuItem();

        // Copy the action and accelarator from the menu,
        dummyItem.setAccelerator(menu.getAccelerator());
        dummyItem.setOnAction(menu.getOnAction());

        menu.setAccelerator(null);
        menu.setOnAction(null);

        // Bind the disabled property, otherwise the shortcuts would still activate
        dummyItem.disableProperty().bind(menu.disableProperty());

        // There was unfortunately no better way to do this
        menu.getItems().add(dummyItem);
        menu.setOnShowing(e -> dummyItem.fire());
        menu.setOnShown(e -> menu.hide());
    }

    public static void centerStage(Stage stage) {
        var screenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
}
