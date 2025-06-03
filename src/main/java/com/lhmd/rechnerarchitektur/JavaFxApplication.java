package com.lhmd.rechnerarchitektur;

import com.lhmd.rechnerarchitektur.common.FxUtils;
import com.lhmd.rechnerarchitektur.events.*;
import com.lhmd.rechnerarchitektur.styles.ThemeManager;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.*;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.*;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> ExceptionHandler.handle(e));

        context = new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(JavaFxApplication.class)
                .initializers(initializers())
                .build()
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void start(Stage primaryStage) throws IOException {
        var loader = new FXMLLoader(JavaFxApplication.class.getResource("views/main.fxml"));
        loader.setControllerFactory(context::getBean);
        var scene = new Scene(loader.load());

        context.publishEvent(new ResetEvent(this, ResetType.POWERON));

        var themeManager = context.getBean(ThemeManager.class);
        themeManager.registerScene(scene);

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(JavaFxApplication.class.getResource("images/cpu.png").toString()));
        primaryStage.setTitle(ProgramInfo.PROGRAM_NAME);
        primaryStage.show();

        FxUtils.centerStage(primaryStage);
    }

    @Override
    public void stop() {
        context.close();
    }

    private ApplicationContextInitializer<GenericApplicationContext> initializers() {
        return ac -> {
            ac.registerBean(HostServices.class, this::getHostServices);
            ac.registerBean(Parameters.class, this::getParameters);
        };
    }
}
