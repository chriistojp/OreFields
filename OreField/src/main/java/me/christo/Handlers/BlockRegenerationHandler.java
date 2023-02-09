package me.christo.Handlers;


import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BlockRegenerationHandler {


    public static Material getMaterial(int level) {

        switch(level) {
            case 1:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.STONE, 60),
                        new MaterialChance(Material.COAL_ORE, 30),
                        new MaterialChance(Material.IRON_ORE, 10)
                ));
            case 2:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.COAL_ORE, 60),
                        new MaterialChance(Material.IRON_ORE, 30),
                        new MaterialChance(Material.GOLD_ORE, 10)
                ));
            case 3:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.IRON_ORE, 60),
                        new MaterialChance(Material.GOLD_ORE, 30),
                        new MaterialChance(Material.DIAMOND_ORE, 10)
                ));
            case 4:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.GOLD_ORE, 60),
                        new MaterialChance(Material.DIAMOND_ORE, 30),
                        new MaterialChance(Material.EMERALD, 10)
                ));
            case 5:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.DIAMOND_ORE, 60),
                        new MaterialChance(Material.EMERALD_ORE, 30),
                        new MaterialChance(Material.IRON_BLOCK, 10)
                ));
            case 6:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.EMERALD_ORE, 60),
                        new MaterialChance(Material.IRON_BLOCK, 30),
                        new MaterialChance(Material.GOLD_BLOCK, 10)
                ));
            case 7:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.IRON_BLOCK, 60),
                        new MaterialChance(Material.GOLD_BLOCK, 30),
                        new MaterialChance(Material.DIAMOND_BLOCK, 10)
                ));
            case 8:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.GOLD_BLOCK, 60),
                        new MaterialChance(Material.DIAMOND_BLOCK, 30),
                        new MaterialChance(Material.EMERALD_BLOCK, 10)
                ));
            case 9:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.DIAMOND_BLOCK, 70),
                        new MaterialChance(Material.EMERALD_BLOCK, 30)
                ));
            case 10:
                return chooseMaterial(Arrays.asList(
                        new MaterialChance(Material.DIAMOND_BLOCK, 30),
                        new MaterialChance(Material.EMERALD_BLOCK, 70)
                ));
            default:
                return null;
        }
    }

    public static Material chooseMaterial(List<MaterialChance> materialChances) {
        int totalChance = materialChances.stream().mapToInt(MaterialChance::getChance).sum();

        int randomNumber = new Random().nextInt(totalChance) + 1;
        int accumulatedChance = 0;

        for (MaterialChance materialChance : materialChances) {
            accumulatedChance += materialChance.getChance();
            if (randomNumber <= accumulatedChance) {
                return materialChance.getMaterial();
            }
        }
        return materialChances.get(materialChances.size() - 1).getMaterial();
    }
}
