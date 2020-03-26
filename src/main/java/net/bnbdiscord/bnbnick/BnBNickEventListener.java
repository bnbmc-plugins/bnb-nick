package net.bnbdiscord.bnbnick;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class BnBNickEventListener implements Listener {
    private BnBNickPlugin plugin;

    public BnBNickEventListener(BnBNickPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        // TODO: check if this is a new player, and if it is, check if someone else uses their name as a nickname

        try {
            String nickname = plugin.getDatabase().getNickname(e.getPlayer().getUniqueId().toString());
            Utils.applyNickname(e.getPlayer(), nickname);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
