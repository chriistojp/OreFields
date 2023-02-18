package me.christo.GUIs;

import me.christo.Handlers.Gui;
import me.christo.Handlers.OreFieldResetHandler;
import me.christo.Main;
import me.christo.Utilities.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OreFieldsGUI {

    public static void open(Player player) {

        Gui gui = new Gui("&b&lOre Fields", 27);
        gui.c();
        gui.noShifties();

        gui.fillRandom(" ", Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        gui.i(10, Material.BEDROCK, "&b&lView your Bedrock", "", "&7This menu will allow you to upgrade", "&7your bedrock.", "", "&7&o((Right-Click to Open))");
        gui.i(13, Material.BEACON, "&b&lOre Fields" ,"", "&7Ore fields are our unqiue spin on the traditional", "&7generator." +
                " Ore fields can be upgraded via the bedrock menu. &7You", "&7can place additional bedrock to expand your ore field.",
                "&7The higher the level the more valuable the blocks will be.");
        gui.i(16, Material.BARRIER, "&c&lRemove your Ore Field", "", "&7Clicking this button will remove your ore field. &7You",
                "&7will recieve all of the bedrock as well as your beacon.", "", "&7&o((Right-Click to Delete))");

        gui.show(player);

        gui.onClick(e -> {

            if(e.getSlot() == 10) {
                BedrockGUI.open(player);
            }
            if(e.getSlot() == 16) {
                //check player balance
                if(Util.checkIslandLeaderPermission(player)) {
                    gui.close(player);
                    OreFieldResetHandler.clearField(player);
                }
            }

        });


    }


}
