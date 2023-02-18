package me.christo.Events;


import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.christo.Handlers.BlockRegenerationHandler;
import me.christo.Main;
import me.christo.Utilities.ConnectionPool;
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

    private ConnectionPool connectionPool = new ConnectionPool("jdbc:mysql://u11_LMPFwUnh55:DWfJy%408f2b3%3D%5E%3D504!s28I3x@172.18.0.1:3306/s11_OreFields", "u11_LMPFwUnh55", "DWfJy@8f2b3=^=504!s28I3x", 20);
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
            SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(e.getPlayer().getUniqueId());
            if(SuperiorSkyblockAPI.getIslandAt(player.getLocation()) == player.getIsland()) {
                String islandName = player.getName() + "_island";


                Statement statement = null;
                ResultSet resultSet = null;
                try (Connection connection = connectionPool.getConnection()){
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM " + islandName + " WHERE location = '" + searchString + "'");
                    if (resultSet.next()) {
                        int level = resultSet.getInt("level");
                        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                            loc.setY(loc.getY() + 1);
                            loc.getBlock().setType(BlockRegenerationHandler.getMaterial(level));
                        }, 40);
                        statement.close();
                    } else {
                        // System.out.println("No matching rows found.");
                    }

                } catch (SQLException exc) {
                    exc.printStackTrace();
                }

            } else {
                p.sendMessage(Util.color("You must be on your island to do this!", true));
                e.setCancelled(true);
            }
        }
    }

}
