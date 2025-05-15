package com.lhmd.rechnerarchitektur;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ExceptionHandler {
    private ExceptionHandler() {
    }

    public static void handle(Throwable e) {
        // TODO necessary?
        // e.printStackTrace();
        handleInternal(e);
    }

    private static void handleInternal(Throwable e) {
        if (e.getCause() != null) {
            handleInternal(e.getCause());
            return;
        }

        Platform.runLater(() -> {
            var alert = new Alert(Alert.AlertType.ERROR, e.toString());
            alert.showAndWait();
        });
    }
}
