package vv0ta3fa9.plugin.referal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.Nullable;
import vv0ta3fa9.plugin.KReferalSystem;

import java.util.Arrays;
import java.util.List;

public class CommandsManager implements CommandExecutor, TabCompleter {

    private final KReferalSystem plugin;

    public CommandsManager(KReferalSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("kreferalsystem.admin")) {
            sender.sendMessage(plugin.getMessagesManager().nopermission());
            return true;
        }

        if (args.length == 0) {
            send(sender, "§cИспользование: /kreferal <reload|info|list>");
            return true;
        }

        String subcommand = args[0].toLowerCase();
        switch (subcommand) {
            case "reload":
                Bukkit.getPluginManager().disablePlugin(plugin);
                Bukkit.getPluginManager().enablePlugin(plugin);
                send(sender, plugin.getMessagesManager().reloadplugin());
                return true;
            case "help":
                send(sender, "&7──── &dCandyReferalSystem &7────");
                send(sender, "&d/candyreferal help &7- &7Выводит данный список.");
                send(sender, "&d/candyreferal reload &7- &7Перезагрузить плагин.");
                send(sender, "&d/candyreferal list &7- &7Выводит список записаных реферальных кодов.");
                send(sender, "&7──────────────────");
                return true;
            case "list":
                send(sender, "&7[&d!&7] &7Список существующих реферальных кодов:" + plugin.getConfigManager().getMediaCodeList().toString());
                return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "reload", "list");
        }
        return List.of();
    }

    private void send(CommandSender sender, String msg) {
        sender.sendMessage(plugin.getConfigManager().COLORIZER.colorize(msg));
    }
}

