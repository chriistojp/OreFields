package me.christo.GUIs;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import me.christo.Handlers.BlockRegenerationHandler;
import me.christo.Handlers.Gui;
import me.christo.Handlers.MaterialChance;
import me.christo.Handlers.SpinningBlockTask;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BedrockGUI {

    public static void open(Player p) {

        Gui gui = new Gui("Your Bedrock", 54);
        gui.fill(new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE), " ");

        SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(p);
        String islandLeader = player.getIslandLeader().getName();

        Connection connection = Util.getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        HashMap<String, Integer> tempBedrockStorage = new HashMap<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `" + islandLeader + "_island`");
            int count = 0;
            while (resultSet.next()) {
                int level = resultSet.getInt("level");
                String location = resultSet.getString("location");
                tempBedrockStorage.put(location, level);
                if(level == 999) {
                    continue;
                }


                gui.i(count, Material.BEDROCK, "&b&lLevel " + level, getLore(level, location));
                count++;
            }
            statement.close();
            connection.close();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }


        gui.show(p);

        gui.onClick(e -> {

            e.setCancelled(true);
            gui.close(p);

            if(e.getCurrentItem().getType() != Material.BEDROCK) {
                return;
            }

            String level = e.getCurrentItem().getItemMeta().getDisplayName();
            level = ChatColor.stripColor(level);
            level = level.replace("Level ", "");
            int bedrockLevel = Integer.parseInt(level);
            if(bedrockLevel >= 10) {
                p.sendMessage(Util.color("This bedrock is already max level", true));
                return;
            }


            //Animation
            Bukkit.getWorld("SuperiorWorld").playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);



            List<String> lore = e.getCurrentItem().getItemMeta().getLore();
            String toReplace = ChatColor.stripColor(lore.get(2));
            toReplace = toReplace.replace("Location: (", "");
            toReplace =  toReplace.replace(")", "");
            // Bukkit.broadcastMessage(toReplace);
            String[] locationString = toReplace.split(" ");

            Location loc = new Location(Bukkit.getWorld("SuperiorWorld"), Double.parseDouble(locationString[0]), Double.parseDouble(locationString[1]), Double.parseDouble(locationString[2]));
            loc.setY(loc.getY() + 1);
            loc.getBlock().setType(Material.AIR);
            loc.setY(loc.getY() - 1);
            SpinningBlockTask task = new SpinningBlockTask(loc);
            task.start();
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {

                Bukkit.getWorld("SuperiorWorld").strikeLightning(loc);
                task.stop();

            }, 100);

            //Animation End

            //Replacing the block
            loc.setY(loc.getY() + 1);
            loc.getBlock().setType(BlockRegenerationHandler.getMaterial(tempBedrockStorage.get(toReplace)));

            //loc is the location of the bedrock
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE " + islandLeader + "_island " + "SET level=? WHERE location=?");
                preparedStatement.setInt(1, tempBedrockStorage.get(toReplace) + 1); // new level value
                preparedStatement.setString(2, toReplace); // location value

                int rowsUpdated = preparedStatement.executeUpdate();
                // Bukkit.broadcastMessage(rowsUpdated + " ");


            } catch (SQLException exception) {

            }

            tempBedrockStorage.clear();

        });

    }

    public static List<String> getLore(int level, String location) {

        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Price to Upgrade: &b$1234");
        lore.add("&7Location: &b(" + location + ")");
        lore.add("");
        lore.add("&b&lChances:");
        lore.add("");
        lore.add(""); //6
        lore.add(""); //7
        lore.add(""); //8
        lore.add(""); //9
        lore.add("");
        lore.add("&7&o(( Right-Click to Upgrade ))");
        switch(level) {

            case 1:
                lore.set(6, "&7Stone → &c0 &o(-60%)");
                lore.set(7, "&7Coal Ore &7→ &a60% &o(+30%)");
                lore.set(8, "&fIron Ore &7→ &a30% &o(+20%)");
                lore.set(9, "&6Gold Ore &7→ &a10% &o(+10%)");
                return lore;
            case 2:
                lore.set(6, "&CCoal Ore → &c0 &o(-60%)");
                lore.set(7, "&fIron Ore &7→ &a60% &o(+30%)");
                lore.set(8, "&6Gold Ore &7→ &a30% &o(+20%)");
                lore.set(9, "&bDiamond Ore &7→ &a10% &o(+10%)");
                return lore;
            case 3:
                lore.set(6, "&fIron Ore → &c0 &o(-60%)");
                lore.set(7, "&6Gold Ore &7→ &a60% &o(+30%)");
                lore.set(8, "&bDiamond Ore &7→ &a30% &o(+20%)");
                lore.set(9, "&2Emerald Ore &7→ &a10% &o(+10%)");
                return lore;
            case 4:
                lore.set(6, "&6Gold Ore → &c0 &o(-60%)");
                lore.set(7, "&bDiamond Ore &7→ &a60% &o(+30%)");
                lore.set(8, "&2Emerald ore &7→ &a30% &o(+20%)");
                lore.set(9, "&f&lIron Block &7→ &a10% &o(+10%)");
                return lore;
            case 5:
                lore.set(6, "&bDiamond Ore → &c0 &o(-60%)");
                lore.set(7, "&2Emerald Ore &7→ &a60% &o(+30%)");
                lore.set(8, "&f&lIron Block &7→ &a30% &o(+20%)");
                lore.set(9, "&6&lGold Block &7→ &a10% &o(+10%)");
                return lore;
            case 6:
                lore.set(6, "&2Emerald Ore → &c0 &o(-60%)");
                lore.set(7, "&f&lIron Block &7→ &a60% &o(+30%)");
                lore.set(8, "&6&lGold Block &7→ &a30% &o(+20%)");
                lore.set(9, "&b&lDiamond Block &7→ &a10% &o(+10%)");
                return lore;
            case 7:
                lore.set(6, "&f&lIron Block → &c0 &o(-60%)");
                lore.set(7, "&6&lGold Block &7→ &a60% &o(+30%)");
                lore.set(8, "&b&lDiamond Block &7→ &a30% &o(+20%)");
                lore.set(9, "&2&lEmerald Block &7→ &a10% &o(+10%)");
                return lore;
            case 8:
                lore.set(6, "&6&lGold Block → &c0 &o(-60%)");
                lore.set(7, "&b&lDiamond Block &7→ &a70% &o(+40%)");
                lore.set(8, "&2&lEmerald Block &7→ &a30% &o(+20%)");
                lore.remove(9);
                return lore;
            case 9:
                lore.remove(6);
                lore.remove(5);
                lore.set(7, "&b&lDiamond Block &7→ &c30% &o(-40%)");
                lore.set(8, "&2&lEmerald Block &7→ &a70% &o(+20%)");
                return lore;
            case 10:
                lore.set(6, "&a&LMAX LEVEL");
                lore.set(7, "&b&lDiamond Block &7→ &c30%");
                lore.set(8, "&2&lEmerald Block &7→ &a70%");
                lore.remove(9);
                return lore;
        }


        return lore;
    }

}
