package com.laymain.kafkatool.plugin;

import com.kafkatool.ui.MainFrame;

import javax.swing.*;
import java.io.File;
import java.util.Optional;

class ConfigurationLoader {

    private static final String XDG_CONFIG_HOME = "XDG_CONFIG_HOME";
    private static final String PLUGIN_NAME = "kafkatool.plugin.avro";
    private static final String CONFIG_DIRECTORY_DEFAULT = ".config";

    static String getConfigurationFileName() {
        String configDirectory = initializeConfigLocation();
        String configurationFile = "config";

        return String.join(File.separator, configDirectory, configurationFile);
    }

    private static String initializeConfigLocation() {
        String configBaseDirectory = Optional.ofNullable(System.getenv(XDG_CONFIG_HOME))
                .orElse(String.join(File.separator, System.getProperty("user.home"), CONFIG_DIRECTORY_DEFAULT));

        String configDirectory = String.join(File.separator, configBaseDirectory, PLUGIN_NAME);
        File file = new File(configDirectory);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                final String message = "Failed to initialize config directory";
                AvroCustomMessageDecorator.LOGGER.error(message);
                JOptionPane.showMessageDialog(MainFrame.getInstance(), message, message, JOptionPane.ERROR_MESSAGE);
            }
        }
        return configDirectory;
    }
}