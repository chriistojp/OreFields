package me.christo.Handlers;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ActivationHandler {

    //if len of the existing table < 9 then not active
    //if len of the table == 9 activated
    // if len of the table > 9 nothing

    public boolean checkBedrock(Location beaconLocation) {
        int x = beaconLocation.getBlockX();
        int y = beaconLocation.getBlockY();
        int z = beaconLocation.getBlockZ();
        World world = beaconLocation.getWorld();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y; j++) {
                for (int k = z - 1; k <= z + 1; k++) {
                    Block block = world.getBlockAt(i, j, k);
                    if (block.getType() != Material.BEDROCK) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
