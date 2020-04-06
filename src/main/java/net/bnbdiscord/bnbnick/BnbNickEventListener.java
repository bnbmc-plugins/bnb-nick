package net.bnbdiscord.bnbnick;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.UUID;

public class BnbNickEventListener implements Listener {
    private BnbNickPlugin plugin;

    public BnbNickEventListener(BnbNickPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            String uuid = plugin.getDatabase().getUUID(e.getPlayer().getName());
            if (uuid != null) { // Name is used as a nickname
                plugin.getDatabase().unsetNickname(uuid);
                Player old = Bukkit.getPlayer(UUID.fromString(uuid));
                if (old != null) {
                    Utils.applyNickname(old, null);
                    Utils.sendMessage(old, "BAD NEWS", "Someone with your nickname as their username joined, so your " +
                            "nickname has been removed.", ChatColor.RED);
                }
            }

            String nickname = plugin.getDatabase().getNickname(e.getPlayer().getUniqueId().toString());
            Utils.applyNickname(e.getPlayer(), nickname);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
