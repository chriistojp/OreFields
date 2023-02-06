package me.christo.Utilities;

import org.bukkit.ChatColor;

public class Util {

    public static String color(String s) {
        String prefix = "&b&lSKYSURGE | ";
        return ChatColor.translateAlternateColorCodes('&', prefix + "&7" + s);
    }

}
