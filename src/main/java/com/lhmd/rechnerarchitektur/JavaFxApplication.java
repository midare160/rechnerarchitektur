package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import com.lhmd.rechnerarchitektur.views.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootApplication
public class JavaFxApplication extends Application {
    public static void main(String[] args) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> ExceptionHandler.handle(e));

        Configuration.initialize();
        ThemeManager.initialize();

        launch(args);
    }

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(JavaFxApplication.class)
                .build()
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        this.context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        context.close();
    }

    public static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }

    @Component
    public static class StageListener implements ApplicationListener<StageReadyEvent> {
        private final MainView mainView;

        public StageListener(MainView mainView) {
            this.mainView = mainView;
        }

        @Override
        public void onApplicationEvent(StageReadyEvent event) {
            var stage = event.getStage();

            var scene = new Scene(mainView);
            ThemeManager.applyCurrentStylesheet(scene);

            stage.setScene(scene);
            stage.setTitle(ProgramInfo.PROGRAM_NAME);
            stage.setOnCloseRequest(e -> {
                Runner.unchecked(Configuration::save);
                mainView.shutdownCpu();
            });
            stage.centerOnScreen();

            stage.show();
        }
    }
}
