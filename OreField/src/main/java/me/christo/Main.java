package me.christo;

import me.christo.Events.BedrockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.getServer().getPluginManager().registerEvents(new BedrockPlaceEvent(), this);
        connect();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void connect() {

        String dbURL = "jdbc:mysql://u11_LMPFwUnh55:DWfJy%408f2b3%3D%5E%3D504!s28I3x@172.18.0.1:3306/s11_OreFields";
        String user = "u11_LMPFwUnh55";
        String pass = "DWfJy@8f2b3=^=504!s28I3x";
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException e) {

        }
    }

}
