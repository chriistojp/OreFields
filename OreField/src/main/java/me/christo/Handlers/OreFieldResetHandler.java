package me.christo.Handlers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;
import java.util.HashMap;

public class OreFieldResetHandler {


    public static void clearField(Player p) {
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

        p.getInventory().addItem(i);

        Connection connection = Main.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        HashMap<String, Integer> tempBedrockStorage = new HashMap<>();

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `" + p.getName() + "_island`");
            while (resultSet.next()) {
                int level = resultSet.getInt("level");
                String location = resultSet.getString("location");

                String[] locations = location.split(" ");
                double x = Double.parseDouble(locations[0]);
                double y = Double.parseDouble(locations[1]);
                double z = Double.parseDouble(locations[2]);
                Bukkit.getWorld("SuperiorWorld").getBlockAt(new Location(Bukkit.getWorld("SuperiorWorld"), x, y, z)).setType(Material.AIR);

                if (level == 999) {
                    Bukkit.getWorld("SuperiorWorld").getBlockAt(new Location(Bukkit.getWorld("SuperiorWorld"), x, y, z)).setType(Material.AIR);
                    continue;
                }
                p.getInventory().addItem(new ItemStack(Material.BEDROCK));



            }
            Util.query("DELETE FROM " + p.getName() + "_island");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static Integer getTotalRows(Player p) {


        int rowCount = 0;
        // Establish database connection
        try {
            Connection conn = Util.getConnection();

            // Create SQL statement to count rows in table
            String countQuery = "SELECT COUNT(*) FROM " + p.getName() + "_island";
            PreparedStatement countStmt = conn.prepareStatement(countQuery);

            // Execute count query and save result as integer
            ResultSet countResult = countStmt.executeQuery();
            if (countResult.next()) {
                rowCount = countResult.getInt(1);
            }

            // Drop table
            String dropQuery = "DROP TABLE " + p.getName() + "_island";
            PreparedStatement dropStmt = conn.prepareStatement(dropQuery);
            dropStmt.executeUpdate();

            // Close all resources
            countResult.close();
            countStmt.close();
            dropStmt.close();
            conn.close();

            // System.out.println("Table " + p.getName() + "_island" + " dropped. Row count: " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
}


