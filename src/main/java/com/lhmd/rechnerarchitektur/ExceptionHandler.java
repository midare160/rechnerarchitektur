package com.lhmd.rechnerarchitektur;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ExceptionHandler {
    public static void handle(Throwable e) {
        if (e.getCause() != null) {
            handle(e.getCause());
            return;
        }

        Platform.runLater(() -> {
            var alert = new Alert(Alert.AlertType.ERROR, e.toString());
            alert.showAndWait();
        });
    }
}
