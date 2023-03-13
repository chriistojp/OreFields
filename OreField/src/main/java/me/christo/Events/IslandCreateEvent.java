package me.christo.Events;

import com.bgsoftware.superiorskyblock.api.island.Island;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandCreateEvent implements Listener {

    @EventHandler
    public void onCreate(com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent e) {

        String islandName = e.getPlayer().getName() + "_island";
        Util.query("CREATE TABLE `s11_OreFields`.`" + islandName + "` ( `location` VARCHAR(200) NOT NULL , `level` INT(200) NOT NULL ) ENGINE = InnoDB;\n");
        Island island = e.getIsland();
        Location loc = island.getTeleportLocation(World.Environment.NORMAL);

        //here we go ffs.
        //set the y location down one to get to the ground level.
        loc.setY(loc.getY() - 1);

        //okay now we gotta generate the orefield. lets place the beacon and set the SQL first. lets go 20 blocks right.
        loc.setX(loc.getX() - 20);

        //lets put it in SQL and set the block to be a beacon. thankfully i made a cool method. lets also get the location
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        Util.query("INSERT INTO `" + islandName + "` (`location`, `level`) VALUES ('" + x + " " + y + " " + z + "', '999')");

        //this was annoying. gotta set the beacon later lol. whatever.
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            loc.getWorld().setType(loc, Material.BEACON);
            loc.getWorld().getBlockAt(loc).setType(Material.BEACON);
            Bukkit.broadcastMessage("set!");
        }, 40);

        //okay we lit. we balling. now lets place 9 bedrock underneath of it. not looking forward to doing this lmao.
        //im gonna test to make sure the beacon is placed first. if i cant do that right i can do anything right.

        Bukkit.broadcastMessage(loc.getX() + " " + loc.getY() + " " + loc.getZ());


        //okay time to set the bedrock.
       // Util.query("INSERT INTO `" + islandName + "` (`location`, `level`) VALUES ('" + locationString + "', '1')");

        int centerX = loc.getBlockX();
        int centerY = loc.getBlockY() - 1;
        int centerZ = loc.getBlockZ();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
            for (int i = centerX - 1; i <= centerX + 1; i++) {
                for (int b = centerZ - 1; b <= centerZ + 1; b++) {
                    Bukkit.broadcastMessage(i +  " " + b);
                    Block block = loc.getWorld().getBlockAt(i, centerY, b);
                    block.setType(Material.BEDROCK);
                }
            }
        }, 40);


    }

}
