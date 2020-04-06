package net.bnbdiscord.bnbnick.commands;

import net.bnbdiscord.bnbnick.BnbNickPlugin;
import net.bnbdiscord.bnbnick.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommandExecutor implements CommandExecutor {
    private BnbNickPlugin plugin;

    public NickCommandExecutor(BnbNickPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Utils.sendMessage(sender, "NOPE", "Go away, you nameless person", ChatColor.RED);
            return true;
        }

        if (args.length == 0) {
            if(plugin.getDatabase().unsetNickname(((Player) sender).getUniqueId().toString())) {
                Utils.applyNickname((Player) sender, null);
                Utils.sendMessage(sender, "SUCCESS", "Your nickname has been removed.", ChatColor.GREEN);
            } else {
                Utils.sendMessage(sender, "FAIL", "Nothing has changed, did you even have a nickname?", ChatColor.RED);
            }
        } else {
            String nickname = String.join(" ", args);
            if (Bukkit.getPlayer(nickname) != null) {
                Utils.sendMessage(sender, "NOPE", "Someone else already has that name.", ChatColor.RED);
                return true;
            }

            if (plugin.getDatabase().setNickname(((Player) sender).getUniqueId().toString(), nickname)) {
                Utils.applyNickname((Player) sender, nickname);
                Utils.sendMessage(sender, "PRIVACY", "Your nickname has been changed. (However, people can still see" +
                        " who you are using /whois!)", ChatColor.GREEN);
            } else {
                Utils.sendMessage(sender, "UH OH", "Nothing has changed, no privacy for you (maybe someone else " +
                        "already has that name?)", ChatColor.RED);
            }
        }
        return true;
    }
}
