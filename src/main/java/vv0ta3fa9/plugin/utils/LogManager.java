package vv0ta3fa9.plugin.utils;


import vv0ta3fa9.plugin.KReferalSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager {
    private final KReferalSystem plugin;
    private File logFile;

    public LogManager(KReferalSystem plugin) {
        this.plugin = plugin;
        createLogFile();
    }

    private void createLogFile() {
        logFile = new File(plugin.getDataFolder(), "log.txt");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для записи лога.
     * @param time Время события
     * @param playerName Ник игрока
     * @param referalCode Реферальный код
     */
    public void writeLog(String time, String playerName, String referalCode) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            writer.write(time + " | " + playerName + " | " + referalCode + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

