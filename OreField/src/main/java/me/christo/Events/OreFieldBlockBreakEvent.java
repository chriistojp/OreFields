package me.christo.Events;


import me.christo.Handlers.BlockRegenerationHandler;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OreFieldBlockBreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Location loc = e.getBlock().getLocation();
        loc.setY(loc.getY() - 1);
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        if (loc.getBlock().getType() == Material.BEDROCK) {
            String searchString = x + " " + y + " " + z;

            Connection connection = Util.getConnection();
            Statement statement = null;
            ResultSet resultSet = null;
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery("SELECT * FROM christo_island WHERE location = '" + searchString + "'");
                if (resultSet.next()) {
                    int level = resultSet.getInt("level");
                    Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                        loc.setY(loc.getY() + 1);
                        loc.getBlock().setType(BlockRegenerationHandler.getMaterial(level));
                    }, 40);
                } else {
                    System.out.println("No matching rows found.");
                }

            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        }
    }

}
