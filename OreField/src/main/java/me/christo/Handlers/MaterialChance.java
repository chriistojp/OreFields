package me.christo.Handlers;


import org.bukkit.Material;

import java.util.List;

public class MaterialChance {
    private Material material;
    private int chance;

    public MaterialChance(Material material, int chance) {
        this.material = material;
        this.chance = chance;
    }

    public Material getMaterial() {
        return material;
    }

    public int getChance() {
        return chance;
    }

}
