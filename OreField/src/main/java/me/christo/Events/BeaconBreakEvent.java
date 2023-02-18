package me.christo.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BeaconBreakEvent implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(e.getBlock().getType() == Material.BEACON) {

            e.setCancelled(true);

        }
    }
}
