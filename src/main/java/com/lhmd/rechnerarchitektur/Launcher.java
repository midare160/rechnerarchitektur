package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Launcher extends Application {
    public static void main(String[] args) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> ExceptionHandler.handle(e));

        Configuration.initialize();
        ThemeManager.initialize();

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        var loader = new FXMLLoader(Launcher.class.getResource("views/main-view.fxml"));
        var scene = new Scene(loader.load());
        var controller = loader.<MainController>getController();

        stage.setTitle(ProgramInfo.PROGRAM_NAME);
        stage.setScene(scene);
        controller.setStage(stage);
        ThemeManager.applyCurrentStylesheet(scene);

        stage.setOnCloseRequest(e -> {
            try {
                Configuration.save();
            } catch (IOException ex) {
                ExceptionHandler.handle(ex);
            }
        });

        stage.show();
        stage.centerOnScreen();
    }
}
