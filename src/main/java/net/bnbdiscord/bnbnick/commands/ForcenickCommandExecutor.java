package net.bnbdiscord.bnbnick.commands;

import net.bnbdiscord.bnbnick.BnbNickPlugin;
import net.bnbdiscord.bnbnick.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ForcenickCommandExecutor implements CommandExecutor {
    private BnbNickPlugin plugin;

    public ForcenickCommandExecutor(BnbNickPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            Utils.sendMessage(sender, "NOT FOUND", "That player couldn't be found. Are they online?", ChatColor.RED);
            return true;
        }

        if (args.length == 1) {
            if(plugin.getDatabase().unsetNickname(target.getUniqueId().toString())) {
                Utils.applyNickname((Player) sender, null);
                Utils.sendMessage(sender, "SUCCESS", "The nickname has been removed.", ChatColor.GREEN);
            } else {
                Utils.sendMessage(sender, "NO CHANGES", "Nothing has changed, did that player even have a nickname?",
                        ChatColor.RED);
            }
        } else {
            String nickname = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            if (Bukkit.getPlayer(nickname) != null) {
                Utils.sendMessage(sender, "NOPE", "Someone else already has that name.", ChatColor.RED);
                return true;
            }

            if (plugin.getDatabase().setNickname(target.getUniqueId().toString(), nickname)) {
                Utils.applyNickname((Player) sender, nickname);
                Utils.sendMessage(sender, "DONE", "That player's nickname has been changed. (However, people" +
                        " can still see who you are using /whois!)", ChatColor.GREEN);
            } else {
                Utils.sendMessage(sender, "UH OH", "Nothing has changed... (Maybe someone else already has" +
                        " that nickname?)", ChatColor.RED);
            }
        }
        return true;
    }
}
