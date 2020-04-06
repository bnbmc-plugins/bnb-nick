package net.bnbdiscord.bnbnick;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NicknameDatabase {
    private BnbNickPlugin plugin;
    private Connection conn;

    public NicknameDatabase(BnbNickPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Sets up the connection to the database
     * @param path path to the database file
     * @throws SQLException
     */
    public void initialise(String path) throws SQLException {
        plugin.getLogger().info("Initialising database");
        conn = DriverManager.getConnection("jdbc:sqlite:" + path);
        plugin.getLogger().info("Creating tables");
        Statement s = conn.createStatement();
        s.executeUpdate("CREATE TABLE IF NOT EXISTS nicknames (" +
                "id TEXT PRIMARY KEY ON CONFLICT REPLACE," +
                "nickname TEXT NOT NULL UNIQUE)");
        plugin.getLogger().info("Successfully initialised database");
    }

    /**
     * Finds the nickname for the player with the provided UUID
     * @param uuid the player's UUID
     * @return the player's nickname
     * @throws SQLException
     */
    public String getNickname(String uuid) throws SQLException {
        PreparedStatement s = conn.prepareStatement("SELECT nickname FROM nicknames WHERE id = ?");
        s.setString(1, uuid);
        ResultSet res = s.executeQuery();
        if (!res.next()) return null;
        return res.getString("nickname");
    }

    /**
     * Finds the UUID of the player with the provided nickname
     * @param nickname the player's nickname
     * @return the player's UUID
     * @throws SQLException
     */
    public String getUUID(String nickname) throws SQLException {
        PreparedStatement s = conn.prepareStatement("SELECT id FROM nicknames WHERE nickname = ?");
        s.setString(1, nickname);
        ResultSet res = s.executeQuery();
        if (!res.next()) return null;
        return res.getString("id");
    }

    /**
     * Sets the nickname for a player
     * @param uuid the player's UUID
     * @param nickname the new nickname
     * @return true if the nickname has been updated, false otherwise
     */
    public boolean setNickname(String uuid, String nickname) {
        try {
            PreparedStatement s = conn.prepareStatement("INSERT INTO nicknames VALUES (?, ?)");
            s.setString(1, uuid);
            s.setString(2, nickname);
            return s.executeUpdate() != 0;
        } catch (SQLException e) {
            // Silently fail
            return false;
        }
    }

    /**
     * Unsets the nickname for a player
     * @param uuid the player's UUID
     * @return true if changes were made, false otherwise
     */
    public boolean unsetNickname(String uuid) {
        try {
            PreparedStatement s = conn.prepareStatement("DELETE FROM nicknames WHERE id = ?");
            s.setString(1, uuid);
            return s.executeUpdate() != 0;
        } catch (SQLException e) {
            // Silently fail
            return false;
        }
    }

    /**
     * Find the UUID and nickname with either one provided
     * @param query either a nickname or a UUID
     * @return UUID and nickname
     */
    public NameLookupResult lookup(String query) {
        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM nicknames WHERE id = ?  OR id = ? OR nickname = ?");
            s.setString(1, query);
            Player p = Bukkit.getPlayer(query);
            s.setString(2, p == null ? query : p.getUniqueId().toString());
            s.setString(3, query);
            ResultSet res = s.executeQuery();
            if (!res.next()) return null;
            return new NameLookupResult(res.getString("id"), res.getString("nickname"));
        } catch (SQLException e) {
            // Silently fail
            return null;
        }
    }

    /**
     * Closes the connection to the database
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        conn.close();
        plugin.getLogger().info("Connection to the database closed");
    }

    public static class NameLookupResult {
        public final String uuid;
        public final String nickname;

        public NameLookupResult(String uuid, String nickname) {
            this.uuid = uuid;
            this.nickname = nickname;
        }
    }
}
