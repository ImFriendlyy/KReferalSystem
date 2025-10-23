package vv0ta3fa9.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.sqlite.SQLiteDataSource;

import vv0ta3fa9.plugin.KReferalSystem.utils.*;
import vv0ta3fa9.plugin.referal.CommandsManager;
import vv0ta3fa9.plugin.referal.ReferalCommand;
import vv0ta3fa9.plugin.utils.*;

import javax.sql.DataSource;
import java.io.File;

public final class KReferalSystem extends JavaPlugin {

    private ConfigManager configManager;
    private DataManager dataManager;
    private LogManager logManager;
    private MessagesManager messagesManager;
    private Utils utils;
    private DataSource dataSource;

    @Override
    public void onEnable() {
        try {
            configManager = new ConfigManager(this);
            configManager.setupColorizer();
            utils = new Utils(this);
            messagesManager = new MessagesManager(this);
            dataManager = new DataManager(this, dataSource);
        } catch (Exception e) {
            getLogger().severe("Ошибка загрузки конфигурации плагина!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        setupDataBase();
        if (configManager.getLog()) {
            logManager = new LogManager(this);
        }
        
        registerCommands();
        getLogger().info("&&fПлагин &aуспешно загружен");
        getLogger().info("&fDeveloper: #C102FAvv0ta3fa9");
        getLogger().info("&fVersion: #C102FAv" + this.getDescription().getVersion());

    }

    @Override
    public void onDisable() {}

    private void registerCommands() {
        if (getCommand("kreferal") != null) getCommand("kreferal").setExecutor(new CommandsManager(this));
        else getLogger().severe("Команда 'kreferal' не найдена в plugin.yml!");

        if (getCommand("referal") != null) getCommand("referal").setExecutor(new ReferalCommand(this));
        else getLogger().severe("Команда 'candyreferal' не найдена в plugin.yml!");

    }

    private void setupDataBase() {
        try {
            switch (configManager.getDataBaseType()) {
                case "sqlite":
                    File dbFile = new File(getDataFolder(), "data.db");
                    SQLiteDataSource sqLite = new SQLiteDataSource();
                    sqLite.setUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());
                    this.dataSource = sqLite;
                    getLogger().info("[INFO] Используется локальная SQLite база: " + dbFile.getName());
                    return;
                case "mysql":
                    getLogger().severe("ахуел?");
                default:
                    getLogger().severe("Внимание! Не удалось идентифиицировать тип базы данных в config.yml");
            }
        } catch (Exception e) {
            getLogger().severe("Ошибка при настройке базы данных: " + e.getMessage());
        }
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public MessagesManager getMessagesManager() {
        return messagesManager;
    }
    public Utils getUtils() {
        return utils;
    }
    public DataManager getDataManager() {
        return dataManager;
    }
    public LogManager getLogManager() {
        return logManager;
    }
}
