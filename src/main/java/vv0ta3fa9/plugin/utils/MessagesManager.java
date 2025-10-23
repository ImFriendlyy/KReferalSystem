package vv0ta3fa9.plugin.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import vv0ta3fa9.plugin.KReferalSystem;

import java.io.File;

public class MessagesManager {
    private final KReferalSystem plugin;
    private FileConfiguration messagesconfig;
    private File messagesConfigFile;

    public MessagesManager(KReferalSystem plugin) {
        this.plugin = plugin;
        loadMessagesFile();
    }

    public void loadMessagesFile() {
        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messagesconfig = YamlConfiguration.loadConfiguration(messagesConfigFile);
    }

    public String nopermission() {
        return messagesconfig.getString("system.no-permission", "&7[&d!&7] §cУ тебя нет прав.");
    }
    public String reloadplugin() {
        return messagesconfig.getString("system.reload-plugin", "&7[&d!&7] §aБип-пуп успешная перезагрузкая");
    }
    public String playeronly() {
        return messagesconfig.getString("system.player-only", "&7[&d!&7] §cЭта команда доступна только из консоли!");
    }
    public String notfind() {
        return messagesconfig.getString("not-find", "&7[&c!&7] &cПромо-код не найден.");
    }
    public String complete() {
        return messagesconfig.getString("complete","&7[&a!&7] &aПромо-код активирован!");
    }
    public String alreadycomplete() {
        return messagesconfig.getString("already-complete","&7[&c!&7] &cВы уже активировали какой-то промокод.");
    }
}
