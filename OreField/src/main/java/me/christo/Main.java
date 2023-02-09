package me.christo;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import me.christo.Commands.GiveOreFieldCommand;
import me.christo.Events.BedrockPlaceEvent;
import me.christo.Events.OreFieldBlockBreakEvent;
import me.christo.Events.OreFieldPlaceEvent;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.getServer().getPluginManager().registerEvents(new BedrockPlaceEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OreFieldPlaceEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OreFieldBlockBreakEvent()
                , this);
        getCommand("giveorefield").setExecutor(new GiveOreFieldCommand());



        //SQL Connect Statement


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
            Statement statement = conn.createStatement();
            String query = "CREATE TABLE `s11_OreFields`.`christo_island` ( `location` INT NOT NULL , `level` INT NOT NULL ) ENGINE = InnoDB;";
            statement.executeUpdate(query);
            statement.close();
            if (conn != null) {
                System.out.println("Connected");
            }
        } catch (SQLException e) {

        }
    }

    public static Main getInstance() {
        return instance;
    }

}
