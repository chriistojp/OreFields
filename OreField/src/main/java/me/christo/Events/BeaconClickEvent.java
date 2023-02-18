package me.christo.Events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class BeaconClickEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(e.getClickedBlock() != null) {
            if(e.getClickedBlock().getType() == Material.BEACON) {
                Location loc = e.getClickedBlock().getLocation();
                loc.setY(loc.getY() - 1);
                if(loc.getWorld().getBlockAt(loc).getType() == Material.BEDROCK) {
                    e.setCancelled(true);
                }

            }
        }
    }
}
