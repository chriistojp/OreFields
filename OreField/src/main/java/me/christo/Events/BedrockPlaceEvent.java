package me.christo.Events;

import me.christo.Utilities.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BedrockPlaceEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(e.getBlockPlaced().getType() == Material.BEDROCK) {
            if(e.getBlockAgainst().getType() != Material.BEDROCK) {
                e.setCancelled(true);
                p.sendMessage(Util.color("You must place bedrock against existing bedrock.", true));
            } else {
                Location loc = e.getBlockPlaced().getLocation();
                double x = loc.getX();
                double y = loc.getY();
                double z = loc.getZ();

                String locationString = x + " " + y + " " + z;

                Util.query("INSERT INTO `christo_island` (`location`, `level`) VALUES ('" + locationString + "', '1')");
                //add to config etc
            }
        }
    }

}
