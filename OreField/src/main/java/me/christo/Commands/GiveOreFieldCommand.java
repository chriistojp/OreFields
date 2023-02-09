package me.christo.Commands;


import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class GiveOreFieldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ItemStack i = Util.createItem(Material.BEACON, "&b&lOre Field", "", "&7Lorem ipsum.");

        if (args.length == 1) {
            if(args[0].equalsIgnoreCase("clear")) {
                Util.query("DELETE FROM `christo_island` LIMIT 1000");
                return true;
            }
            if (args[0].equalsIgnoreCase("reset")) {
                Connection conn = Util.getConnection();
                Statement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = conn.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM `christo_island`");

                    // 4. Loop through the result set and print each row
                    while (resultSet.next()) {
                        String column1 = resultSet.getString("location");

                        String[] coordinates = column1.split(" ");
                        for (int z = 0; z < coordinates.length; z++) {
                            System.out.println("coordinates[" + z + "] = " + coordinates[z]);
                        }
                        double x = Double.parseDouble(coordinates[0]);
                        double y = Double.parseDouble(coordinates[1]);
                        double z = Double.parseDouble(coordinates[2]);


                        Location loc = new Location(Bukkit.getWorld("world"), x, y + 1, z);
                        if(loc.getBlock().getType().equals(Material.AIR)) {
                            loc.getBlock().setType(Material.IRON_BLOCK);
                        }

                    }
                } catch (SQLException e) {
                    System.out.println("Error executing query: " + e);
                }

                return true;
            }
            if (Bukkit.getPlayer(args[0]) != null) {
                Bukkit.getPlayer(args[0]).getInventory().addItem(i);
            } else {
                //offline
            }
        }

        return false;
    }
}
