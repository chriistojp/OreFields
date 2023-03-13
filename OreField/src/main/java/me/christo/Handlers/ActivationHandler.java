package me.christo.Handlers;


import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActivationHandler {

    //if len of the existing table < 9 then not active
    //if len of the table == 9 activated
    // if len of the table > 9 nothing


    public static void activate(String islandLeaderName) {

        Connection connection = Util.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Location beaconLocation = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + islandLeaderName + " WHERE level = '999'");
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                // Bukkit.broadcastMessage(location);
                String[] locationList = location.split(" ");
                double x = Double.parseDouble(locationList[0]);
                double y = Double.parseDouble(locationList[1]);
                double z = Double.parseDouble(locationList[2]);
                beaconLocation = new Location(Bukkit.getWorld("SuperiorWorld"), x, y, z);
            }


        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        int x = beaconLocation.getBlockX();
        int y = beaconLocation.getBlockY();
        int z = beaconLocation.getBlockZ();
        World world = beaconLocation.getWorld();


        for (int i = x - 1; i <= x + 1; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                for (int j = z - 1; j <= z + 1; j++) {
                    Block block = world.getBlockAt(finalI, y, j);
                    if (block.getType() == Material.BEACON) {
                        continue;
                    }

                    block.setType(BlockRegenerationHandler.getMaterial(1));


                }
            }, 20);
        }

    }

    public static boolean checkBedrock(String islandLeaderName) {

        Connection connection = Util.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        Location beaconLocation = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + islandLeaderName + " WHERE level = '999'");
            while (resultSet.next()) {
                String location = resultSet.getString("location");
                // Bukkit.broadcastMessage(location);
                String[] locationList = location.split(" ");
                double x = Double.parseDouble(locationList[0]);
                double y = Double.parseDouble(locationList[1]);
                double z = Double.parseDouble(locationList[2]);
                beaconLocation = new Location(Bukkit.getWorld("SuperiorWorld"), x, y, z);
            }


        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        int x = beaconLocation.getBlockX();
        int y = beaconLocation.getBlockY();
        int z = beaconLocation.getBlockZ();
        World world = beaconLocation.getWorld();


        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = z - 1; j <= z + 1; j++) {
                Block block = world.getBlockAt(i, y - 1, j);
                // Bukkit.broadcastMessage(block.getType() + "");
                if (block.getType() != Material.BEDROCK) {
                    return false;
                }
            }
        }


        return true;
    }

}
