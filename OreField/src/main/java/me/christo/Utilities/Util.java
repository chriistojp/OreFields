package me.christo.Utilities;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.handlers.RolesManager;
import com.bgsoftware.superiorskyblock.api.island.PlayerRole;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.management.relation.Role;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static String color(String s, boolean usePrefix) {
        String prefix = "&b&lSKYSURGE | ";
        if (usePrefix) {
            return ChatColor.translateAlternateColorCodes('&', prefix + "&7" + s);
        }
        return ChatColor.translateAlternateColorCodes('&', "&7" + s);
    }

    public static String islandLeaderMessage() {
        return Util.color("You must be the island leader to do this!", true);
    }
    public static String noPermissionMessage() {
        return Util.color("&7You don't have permission to do that!", true);
    }

    public static boolean checkIslandLeaderPermission(Player p) {
        SuperiorPlayer player = SuperiorSkyblockAPI.getPlayer(p);
        return player.getIslandLeader().getName().equals(p.getName());
    }

    public static void query(String query) {


        String dbURL = "jdbc:mysql://u11_LMPFwUnh55:DWfJy%408f2b3%3D%5E%3D504!s28I3x@172.18.0.1:3306/s11_OreFields";
        String user = "u11_LMPFwUnh55";
        String pass = "DWfJy@8f2b3=^=504!s28I3x";
        try {
            Connection conn = Util.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            statement.close();
            if (conn != null) {
                // System.out.println("Connected");
            }
        } catch (SQLException e) {

        }
    }

    public static Connection getConnection() {
        String dbURL = "jdbc:mysql://u11_LMPFwUnh55:DWfJy%408f2b3%3D%5E%3D504!s28I3x@172.18.0.1:3306/s11_OreFields";
        String user = "u11_LMPFwUnh55";
        String pass = "DWfJy@8f2b3=^=504!s28I3x";
        try {
            Connection conn = DriverManager.getConnection(dbURL, user, pass);
            if (conn != null) {
                return conn;
            }
        } catch (SQLException e) {

        }
        return null;
    }



    public static ItemStack createItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Util.color(displayName, false));
        List<String> loreList = new ArrayList<>();
        for (String s : lore) {
            loreList.add(Util.color(s, false));
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);


        return item;

    }

}
