package me.christo.Events;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.christo.Handlers.ActivationHandler;
import me.christo.Handlers.BlockRegenerationHandler;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BedrockPlaceEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) throws SQLException {
        Player p = e.getPlayer();
        if (e.getBlockPlaced().getType() == Material.BEDROCK) {
            String face = e.getBlock().getFace(e.getBlockAgainst()).toString();
            if(face.equals("UP") || face.equals("DOWN")) {
                e.setCancelled(true);
                p.sendMessage(Util.color("You cannot place your bedrock on top or beneath other bedrock.", true));
                return;
            }
            if (e.getBlockAgainst().getType() != Material.BEDROCK) {
                e.setCancelled(true);
                p.sendMessage(Util.color("You must place bedrock against existing bedrock.", true));
            } else {
                SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(e.getPlayer().getUniqueId());
                if (SuperiorSkyblockAPI.getIslandAt(player.getLocation()) == player.getIsland()) {
                    if (player.getIslandLeader().equals(player)) {


                        int length = 0;
                        try {
                            Statement stmt = Main.getConnection().createStatement();

                            //need to update this

                            String query = "select count(*) from " + player.getIslandLeader().getName() + "_island";
                            //Executing the query
                            ResultSet rs = stmt.executeQuery(query);
                            //Retrieving the result
                            rs.next();
                            length = rs.getInt(1);
                            stmt.close();
                        } catch (SQLException exc) {

                        }


                        if (length == 8) {
                            if (ActivationHandler.checkBedrock(player.getName() + "_island")) {

                                ActivationHandler.activate(player.getName() + "_island");

                            } else {
                                p.sendMessage("would've activated but not 3x3");
                            }


                        }
                        Location loc = e.getBlockPlaced().getLocation();
                        double x = loc.getX();
                        double y = loc.getY();
                        double z = loc.getZ();

                        String locationString = x + " " + y + " " + z;

                        String islandName = player.getName() + "_island";
                        Util.query("INSERT INTO `" + islandName + "` (`location`, `level`) VALUES ('" + locationString + "', '1')");
                        loc.setY(y + 1);
                        if (length > 8) {
                            loc.getBlock().setType(BlockRegenerationHandler.getMaterial(1));
                        }


                    } else {
                        p.sendMessage(Util.color("You must be on your island to do this!", true));
                    }
                } else {
                    p.sendMessage(Util.color("You must be the island leader to do this!", true));
                }
            }
        }
    }

}
