package com.lhmd.rechnerarchitektur.common;

import com.lhmd.rechnerarchitektur.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class FxUtils {
    public static <T> void loadHierarchy(T controller, String fxmlPath) {
        var loader = new FXMLLoader(Launcher.class.getResource(fxmlPath));
        loader.setRoot(controller);
        loader.setController(controller);

        Runner.runUnchecked(loader::load);
    }

    public static void setAllAnchors(Node child, Double value) {
        AnchorPane.setTopAnchor(child, value);
        AnchorPane.setRightAnchor(child, value);
        AnchorPane.setBottomAnchor(child, value);
        AnchorPane.setLeftAnchor(child, value);
    }

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
}
