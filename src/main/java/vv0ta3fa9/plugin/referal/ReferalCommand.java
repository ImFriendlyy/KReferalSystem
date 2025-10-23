package vv0ta3fa9.plugin.referal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vv0ta3fa9.plugin.KReferalSystem;

import java.time.LocalDateTime;
import java.util.List;

public class ReferalCommand implements CommandExecutor {

    private final KReferalSystem plugin;

    public ReferalCommand(KReferalSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("candyreferalsystem.user")) {
            sender.sendMessage(plugin.getMessagesManager().nopermission());
            return true;
        }

        if (args.length == 0) {
            send(sender, "&7[&c!&7] &7Использование: &c/referal <promo-code>");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessagesManager().playeronly());
            return true;
        }

        List<String> promolist = plugin.getConfigManager().getMediaCodeList();

        String subcommand = args[0].toLowerCase();
        if (promolist.contains(subcommand) && !plugin.getDataManager().getUser().contains(sender.getName().toLowerCase())) {
            runListCommands(sender.getName());
            send(sender, plugin.getMessagesManager().complete());
            LocalDateTime timenow = LocalDateTime.now();
            plugin.getDataManager().addUser(sender.getName(), subcommand, timenow.toString());
            if (plugin.getConfigManager().getLog()) {
                plugin.getLogManager().writeLog(timenow.toString(), sender.getName(), subcommand);
            }
        } else if (promolist.contains(subcommand) && plugin.getDataManager().getUser().contains(sender.getName().toLowerCase())) {
            send(sender, plugin.getMessagesManager().alreadycomplete());
        } else if (!promolist.contains(subcommand)){
            send(sender, plugin.getMessagesManager().notfind());
        }

        return false;
    }

    public void runListCommands(String playerName) {
        List<String> commands = plugin.getConfigManager().getListCommand();
        if (commands == null || commands.isEmpty()) return;

        for (String command : commands) {
            if (command == null || command.trim().isEmpty()) continue;
            command = command.replace("%sender%", playerName);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }
    }

    private void send(CommandSender sender, String msg) {
        sender.sendMessage(plugin.getConfigManager().COLORIZER.colorize(msg));
    }
}
