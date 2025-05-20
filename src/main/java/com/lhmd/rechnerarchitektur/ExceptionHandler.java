package com.lhmd.rechnerarchitektur;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.slf4j.*;

public class ExceptionHandler {
    private ExceptionHandler() {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    public static void handle(Throwable e) {
        LOGGER.warn(e.getMessage(), e);
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
