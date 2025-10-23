package vv0ta3fa9.plugin.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.sqlite.SQLiteDataSource;
import vv0ta3fa9.plugin.KReferalSystem;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private final KReferalSystem plugin;
    protected FileConfiguration data;
    private File dataFile;
    private DataSource dataSource;



    public DataManager(KReferalSystem plugin, DataSource dataSource) {
        this.plugin = plugin;
        this.dataSource = dataSource;
        setupDataSource();
        initTables();
    }


    private void setupDataSource() {
        // SQLite fallback
        try {
            File dbFile = new File(plugin.getDataFolder(), "data.db");
            if (!dbFile.exists()) dbFile.createNewFile();

            SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
            sqLiteDataSource.setUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());
            this.dataSource = sqLiteDataSource;
            plugin.getLogger().info("SQLite база создана: " + dbFile.getAbsolutePath());

        } catch (Exception e) {
            plugin.getLogger().severe("Не удалось создать локальную SQLite базу!");
            e.printStackTrace();
            throw new RuntimeException("Невозможно создать базу данных", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private void initTables() {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (player VARCHAR(64) PRIMARY KEY)";
        String createPromo = "CREATE TABLE IF NOT EXISTS promo (player VARCHAR(64) PRIMARY KEY)";
        String createTime = "CREATE TABLE IF NOT EXISTS time (player VARCHAR(64) PRIMARY KEY)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createUsers);
            stmt.executeUpdate(createPromo);
            stmt.executeUpdate(createTime);
        } catch (SQLException e) {
            plugin.getLogger().severe("[DataBase] Ошибка при создании таблиц: " + e.getMessage());
        }
    }

    public List<String> getUser() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT player FROM users";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) users.add(rs.getString("player"));
        } catch (SQLException e) {
            plugin.getLogger().severe("[DataBase] Ошибка получения списка игроков: " + e.getMessage());
        }
        return users;
    }

    public void addUser(String playerName, String promo, String times) {
        String sql = "INSERT OR IGNORE INTO users (player) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerName.toLowerCase());
            stmt.executeUpdate();
            plugin.getLogger().info("Добавлен игрок: " + playerName);
        } catch (SQLException e) {
            plugin.getLogger().severe("[DataBase|error:1] Ошибка добавления: " + e.getMessage());
        }
        String sql2 = "INSERT OR IGNORE INTO promo (player) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {
            stmt.setString(2, promo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("[DataBase|error:2] Ошибка добавления: " + e.getMessage());
        }
        String sql3 = "INSERT OR IGNORE INTO time (player) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql3)) {
            stmt.setString(3, times);
            stmt.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("[DataBase|error:3] Ошибка добавления: " + e.getMessage());
        }

    }

    public void removeUser(String playerName) {
        String sql = "DELETE FROM users WHERE player = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playerName.toLowerCase());
            stmt.executeUpdate();
            plugin.getLogger().info("Удален игрок: " + playerName);
        } catch (SQLException e) {
            plugin.getLogger().severe("[DataBase] Ошибка удаления: " + e.getMessage());
        }
    }


}
