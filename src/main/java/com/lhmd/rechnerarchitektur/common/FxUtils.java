package com.lhmd.rechnerarchitektur.common;

import com.lhmd.rechnerarchitektur.Launcher;
import javafx.fxml.FXMLLoader;

public class FxUtils {
    public static <T> void loadHierarchy(T controller, String fxmlPath){
        var loader = new FXMLLoader(Launcher.class.getResource(fxmlPath));
        loader.setRoot(controller);
        loader.setController(controller);

        Runner.runUnchecked(loader::load);
    }
}
