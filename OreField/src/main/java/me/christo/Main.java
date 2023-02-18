package me.christo;

import me.christo.Commands.OreFields;
import me.christo.Events.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public final class Main extends JavaPlugin {

    public static Main instance;
    public static HashMap<InetAddress, Integer> ips = new HashMap<InetAddress, Integer>();

    static Connection connection;
    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.getServer().getPluginManager().registerEvents(new BedrockPlaceEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OreFieldPlaceEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OreFieldBlockBreakEvent(), this);
        this.getServer().getPluginManager().registerEvents(new IslandCreateEvent(), this);
        this.getServer().getPluginManager().registerEvents(new IslandDeleteEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BeaconBreakEvent(), this);
        this.getServer().getPluginManager().registerEvents(new BeaconClickEvent(), this);
        this.getServer().getPluginManager().registerEvents(new FireSpreadEvent(), this);
        getCommand("orefields").setExecutor(new OreFields());

        String dbURL = "jdbc:mysql://u11_LMPFwUnh55:DWfJy%408f2b3%3D%5E%3D504!s28I3x@172.18.0.1:3306/s11_OreFields";
        String user = "u11_LMPFwUnh55";
        String pass = "DWfJy@8f2b3=^=504!s28I3x";
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                connection = conn;
            }
        } catch (SQLException e) {

        }



        //SQL Connect Statement


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
            Statement statement = conn.createStatement();
            statement.close();
            if (conn != null) {
                // System.out.println("Connected");
            }
        } catch (SQLException e) {

        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Main getInstance() {
        return instance;
    }

    public static HashMap<InetAddress, Integer> getIps() {
        return ips;
    }

}
