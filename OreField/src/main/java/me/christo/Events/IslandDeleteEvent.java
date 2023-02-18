package me.christo.Events;

import com.bgsoftware.superiorskyblock.api.events.IslandDeleteWarpEvent;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import me.christo.Handlers.OreFieldResetHandler;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class IslandDeleteEvent implements Listener {

    @EventHandler
    public void onDelete(IslandDisbandEvent e) {
        Player p = e.getPlayer().asPlayer();

        int total = OreFieldResetHandler.getTotalRows(e.getPlayer().asPlayer());
        ItemStack i = Util.createItem(Material.BEACON, "&b&lOre Field", "", "&7" +
                        "&7This is an ore field. In order to activate\n",
                "&7this item you have to place bedrock in a 3x3 \n",
                "&7below the beacon. The bedrock under the beacon\n",
                "&7is done for you.\n",
                "\n",
                "&7Once placed executing &b/orefields &7will\n",
                "&7provide all of the information needed regarding\n",
                "&7your ore field\n",
                "\n",
                "&4&l[!] &cPlacing your ore field will remove the block\n",
                "&cdirectly underneath of it.");
        if(total == 0) {
            //they don't have anything
            return;
        }
        if(total == 1) {
            //ore field but no bedrock
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                p.getInventory().addItem(i);
                p.sendMessage(Util.color("You have been given your Ore Field as well as 0 bedrock.", true));
            }, 60);


        } else {

            total -= 1;
            //bedorck and orefield
            int finalTotal = total;
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                p.getInventory().addItem(i);
                p.getInventory().addItem(new ItemStack(Material.BEDROCK, finalTotal));
                p.sendMessage(Util.color("You have been given your Ore Field as well as " + finalTotal + " bedrock.", true));

            }, 60);


        }

    }
}
