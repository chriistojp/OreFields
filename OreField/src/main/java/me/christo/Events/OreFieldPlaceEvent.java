package me.christo.Events;


import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.christo.Utilities.Util;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class OreFieldPlaceEvent implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(e.getBlock().getType() == Material.BEACON) {
            if(e.getPlayer().getItemInHand().hasItemMeta()) {
                if (e.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {


                    SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(e.getPlayer().getUniqueId());
                    if(SuperiorSkyblockAPI.getIslandAt(player.getLocation()) == player.getIsland()) {
                        if(player.getIslandLeader().equals(player)) {
                            //is island leader
                            //allow place



                        }
                    }


                    Location loc = e.getBlockPlaced().getLocation();
                    loc.setY(loc.getY() - 1);

                    loc.getBlock().setType(Material.BEDROCK);
                }
            }
        }
    }


}
