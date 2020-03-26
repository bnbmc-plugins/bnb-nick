package net.bnbdiscord.bnbnick;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    public static void sendMessage(CommandSender target, String title, String description, ChatColor color) {
        target.sendMessage("" + color + ChatColor.BOLD + title + "! " + ChatColor.RESET + color + description);
    }

    public static void applyNickname(Player target, String nickname) {
        target.setPlayerListName(nickname);
        target.setDisplayName(nickname);
    }
}
