package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import com.lhmd.rechnerarchitektur.views.MainView;
import javafx.application.Application;
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
    public void start(Stage stage) {
        var scene = new Scene(new MainView());
        ThemeManager.applyCurrentStylesheet(scene);

        stage.setScene(scene);
        stage.setTitle(ProgramInfo.PROGRAM_NAME);
        stage.setOnCloseRequest(e -> Runner.runUnchecked(Configuration::save));
        stage.centerOnScreen();

        stage.show();
    }
}
