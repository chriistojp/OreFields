package me.christo.Commands;


import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.christo.GUIs.OreFieldsGUI;
import me.christo.Handlers.ActivationHandler;
import me.christo.Handlers.SpinningBlockTask;
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OreFields implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ItemStack i = Util.createItem(Material.BEACON, "&b&lOre Field", "", "&7" +
                        "&7This is an ore field. In order to activate\n",
                "&7this item you have to place bedrock in a 3x3 \n",
                "&7below the beacon. The bedrock under the beacon\n",
                "&7is done for you.\n",
                "\n",
                "&7Once placedd executing &b/orefields &7will\n",
                "&7provide all of the information needed regarding\n",
                "&7your ore field\n",
                "\n",
                "&4&l[!] &cPlacing your ore field will remove the block\n",
                "&cdirectly underneath of it.");
        if (sender instanceof Player) {
            Player p = (Player) sender;
            SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(p);
            String islandLeader = player.getIslandLeader().getName() + "_island";
            if(args.length == 0) {
                if(Util.checkIslandLeaderPermission(p)) {
                    try {
                        if (!isTableEmpty(Main.getConnection(), p.getName() + "_island")) {
                            if (ActivationHandler.checkBedrock(islandLeader)) {
                                OreFieldsGUI.open(p);
                            } else {
                                p.sendMessage(Util.color("Your Ore Field has not been activated yet!", true));
                            }
                        } else {
                            p.sendMessage(Util.color("You have not placed an Ore Field!", true));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    p.sendMessage(Util.color("You must be the island leader to do this!", true));

                }

            }
            if(args.length == 1) {
                if (args[0].equals("start")) {


                    Location loc = p.getLocation();
                    loc.setY(loc.getY() + 3);
                    SpinningBlockTask task = new SpinningBlockTask(loc);
                    task.start();
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("give")) {
                    if (Bukkit.getPlayer(args[1]) != null) {
                        Bukkit.getPlayer(args[1]).getInventory().addItem(i);
                        Bukkit.getPlayer(args[1]).sendMessage(Util.color("You have been given an Ore Field!", true));
                        p.sendMessage(Util.color("You gave an Ore Field to &b" + Bukkit.getPlayer(args[1]).getName() + "&7.", true));
                    }
                }
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("clear")) {
                    Util.query("DELETE FROM `" + islandLeader + "` LIMIT 1000");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reset")) {
                    Connection conn = Util.getConnection();
                    Statement statement = null;
                    ResultSet resultSet = null;
                    try {

                        resultSet = statement.executeQuery("SELECT * FROM `" + islandLeader + "`");
                        statement = conn.createStatement();

                        // 4. Loop through the result set and print each row
                        while (resultSet.next()) {
                            String column1 = resultSet.getString("location");

                            String[] coordinates = column1.split(" ");
                            for (int z = 0; z < coordinates.length; z++) {
                                // System.out.println("coordinates[" + z + "] = " + coordinates[z]);
                            }
                            double x = Double.parseDouble(coordinates[0]);
                            double y = Double.parseDouble(coordinates[1]);
                            double z = Double.parseDouble(coordinates[2]);


                            Location loc = new Location(Bukkit.getWorld("world"), x, y + 1, z);
                            if (loc.getBlock().getType().equals(Material.AIR)) {
                                loc.getBlock().setType(Material.IRON_BLOCK);
                            }

                        }
                    } catch (SQLException e) {
                        // System.out.println("Error executing query: " + e);
                    }

                    return true;
                }
                if (Bukkit.getPlayer(args[0]) != null) {
                    Bukkit.getPlayer(args[0]).getInventory().addItem(i);
                } else {
                    //offline
                }
            }

        }
        return false;
    }

    public static boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName);
            rs.next();
            int count = rs.getInt(1);
            return count == 0;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
}
