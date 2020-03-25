package net.bnbdiscord.bnbnick;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {
    public static void sendMessage(CommandSender target, String title, String description, ChatColor color) {
        target.sendMessage("" + color + ChatColor.BOLD + title + "! " + ChatColor.RESET + color + description);
    }
}
