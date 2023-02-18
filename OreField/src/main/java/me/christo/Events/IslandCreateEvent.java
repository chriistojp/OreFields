package me.christo.Events;

import me.christo.Utilities.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class IslandCreateEvent implements Listener {

    @EventHandler
    public void onCreate(com.bgsoftware.superiorskyblock.api.events.IslandCreateEvent e) {

        String islandName = e.getPlayer().getName() + "_island";
        Util.query("CREATE TABLE `s11_OreFields`.`" + islandName + "` ( `location` VARCHAR(200) NOT NULL , `level` INT(200) NOT NULL ) ENGINE = InnoDB;\n");

    }

}
