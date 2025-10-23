package vv0ta3fa9.plugin.utils;

import vv0ta3fa9.plugin.KReferalSystem;
import vv0ta3fa9.plugin.utils.color.Colorizer;
import vv0ta3fa9.plugin.utils.color.impl.LegacyAdvancedColorizer;
import vv0ta3fa9.plugin.utils.color.impl.LegacyColorizer;
import vv0ta3fa9.plugin.utils.color.impl.MiniMessageColorizer;
import vv0ta3fa9.plugin.utils.color.impl.VanillaColorizer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {
    private final KReferalSystem plugin;
    protected FileConfiguration config;
    private File configFile;
    public Colorizer COLORIZER;


    public ConfigManager(KReferalSystem plugin) {
        this.plugin = plugin;
        loadConfigFiles();
    }

    public void loadConfigFiles() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) plugin.saveResource("config.yml", false);
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public int getInt(String path, int def) {
        return config.contains(path) ? config.getInt(path) : def;
    }

    public long getLong(String path, long def) {
        return config.contains(path) ? config.getLong(path) : def;
    }

    public boolean getBoolean(String path, boolean def) {
        return config.contains(path) ? config.getBoolean(path) : def;
    }

    public String getString(String path, String def) {
        return config.contains(path) ? config.getString(path) : def;
    }

    public List<String> getStringList(String path) {

        return config.getStringList(path);
    }


    public String getDataBaseType() {
        return getString("database", "SQLITE").toLowerCase();
    }

    public void setupColorizer() {
        COLORIZER = switch (getString("serializer", "LEGACY").toUpperCase()) {
            case "MINIMESSAGE" -> new MiniMessageColorizer();
            case "LEGACY" -> new LegacyColorizer(plugin);
            case "LEGACY_ADVANCED" -> new LegacyAdvancedColorizer(plugin);
            default -> new VanillaColorizer(plugin);
        };
    }

    public Boolean getdebug() {
        return getBoolean("debug", false);
    }
    public Boolean getLog() {
        return getBoolean("log", true);
    }

    public List<String> getMediaCodeList() {
        return getStringList("media-code-list");
    }
    public List<String> getListCommand() {
        return getStringList("command-on-complete");
    }


}
