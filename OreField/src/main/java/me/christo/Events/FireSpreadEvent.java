package me.christo.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class FireSpreadEvent implements Listener {

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        if (event.getBlock().getType() == Material.FIRE) {
            Block newBlock = event.getNewState().getBlock();
            if (newBlock.getType().isFlammable()) {
                event.setCancelled(true);
                // Fire has spread to a flammable block
                // Do something here
            }
        }
    }

}
