package com.lhmd.rechnerarchitektur.configuration;

import com.lhmd.rechnerarchitektur.common.Runner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.*;

import java.io.IOException;
import java.nio.file.*;

@Component
@Lazy
public class UserConfigService implements AutoCloseable {
    private static final Path FILE_PATH = Path.of("user.yml");

    private final Yaml yaml;

    private UserConfig userConfig;

    public UserConfigService() {
        yaml = new Yaml(getLoaderOptions(), getDumperOptions());
    }

    @Override
    public synchronized void close() throws IOException {
        if (userConfig == null) {
            return;
        }

        try (var writer = Files.newBufferedWriter(FILE_PATH)) {
            yaml.dump(userConfig, writer);
        }
    }

    public synchronized UserConfig config() {
        if (userConfig == null) {
            userConfig = Runner.unchecked(this::load);
        }

        return userConfig;
    }

    private LoaderOptions getLoaderOptions() {
        var options = new LoaderOptions();
        options.setTagInspector(t -> t.getClassName().equals(UserConfig.class.getName()));

        return options;
    }

    private DumperOptions getDumperOptions() {
        var options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        return options;
    }

    private UserConfig load() throws IOException {
        if (Files.notExists(FILE_PATH)) {
            return new UserConfig();
        }

        try (var reader = Files.newBufferedReader(FILE_PATH)) {
            return yaml.load(reader);
        }
    }
}
