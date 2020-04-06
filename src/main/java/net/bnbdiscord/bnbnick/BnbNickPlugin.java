package net.bnbdiscord.bnbnick;

import net.bnbdiscord.bnbnick.commands.NickCommandExecutor;
import net.bnbdiscord.bnbnick.commands.WhoisCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BnbNickPlugin extends JavaPlugin {
    private NicknameDatabase database;

    public BnbNickPlugin() {
        database = new NicknameDatabase(this);
    }

    public NicknameDatabase getDatabase() {
        return database;
    }

    @Override
    public void onEnable() {
        // Setup database
        try {
            getDataFolder().mkdir();
            File file = new File(getDataFolder(), "database.sqlite");
            file.createNewFile();
            database.initialise(file.getPath());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            getLogger().severe("Shit");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        // Set command handlers
        getCommand("nick").setExecutor(new NickCommandExecutor(this));
        getCommand("whois").setExecutor(new WhoisCommandExecutor(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new BnbNickEventListener(this), this);
    }

    @Override
    public void onDisable() {
        try {
            database.closeConnection();
        } catch (SQLException e) {
            getLogger().severe("Database connection could not be closed");
        }
    }
}
