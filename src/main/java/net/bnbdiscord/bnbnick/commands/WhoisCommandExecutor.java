package net.bnbdiscord.bnbnick.commands;

import net.bnbdiscord.bnbnick.BnbNickPlugin;
import net.bnbdiscord.bnbnick.NicknameDatabase;
import net.bnbdiscord.bnbnick.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class WhoisCommandExecutor implements CommandExecutor {
    private BnbNickPlugin plugin;

    public WhoisCommandExecutor(BnbNickPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) return false; // Returning false shows usage

        String query = String.join(" ", args);
        NicknameDatabase.NameLookupResult res = plugin.getDatabase().lookup(query);

        Utils.sendMessage(sender, "RESULTS", "This is what I've found:", ChatColor.GREEN);
        if (res == null) {
            Utils.sendMessage(sender, "SURPRISE", "I actually didn't find anything at all", ChatColor.RED);
            return true;
        }

        sender.sendMessage(ChatColor.BOLD + "UUID: " + ChatColor.RESET + res.uuid);
        sender.sendMessage(ChatColor.BOLD + "Username: " + ChatColor.RESET + Bukkit.getOfflinePlayer(UUID.fromString(res.uuid)).getName());
        sender.sendMessage(ChatColor.BOLD + "Nickname: " + ChatColor.RESET + res.nickname);

        return true;
    }
}
