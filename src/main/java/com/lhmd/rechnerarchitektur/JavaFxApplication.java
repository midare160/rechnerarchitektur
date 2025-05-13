package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.Runner;
import com.lhmd.rechnerarchitektur.themes.ThemeManager;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.*;
import org.springframework.context.support.GenericApplicationContext;

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
        context = new SpringApplicationBuilder()
                .sources(JavaFxApplication.class)
                .initializers(initializers())
                .build()
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(JavaFxApplication.class.getResource("views/main.fxml"));
        loader.setControllerFactory(context::getBean);

        var scene = new Scene(loader.load());
        ThemeManager.applyCurrentStylesheet(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle(ProgramInfo.PROGRAM_NAME);
        primaryStage.setOnCloseRequest(e -> Runner.unchecked(Configuration::save));
        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    private ApplicationContextInitializer<GenericApplicationContext> initializers()
    {
        return ac -> {
            ac.registerBean(HostServices.class, this::getHostServices);
            ac.registerBean(Parameters.class, this::getParameters);
        };
    }
}
