package me.christo.Handlers;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class SpinningBlockTask implements Runnable {
    private ArmorStand armorStand;
    private Location location;
    private World world;
    private int taskId;
    private int angle;

    public SpinningBlockTask(Location location) {

        location.setX(location.getX() + .5);
        location.setZ(location.getZ() + .5);
        this.location = location;
        world = location.getWorld();
        armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setHeadPose(new EulerAngle(0, 0, 0));
        armorStand.setBodyPose(new EulerAngle(0, 0, 0));
        armorStand.setHelmet(new ItemStack(Material.BEDROCK));
        angle = 0;
    }

    public void start() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
                Bukkit.getPluginManager().getPlugins()[0],
                this,
                0,
                1
        );
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
        armorStand.remove();
    }

    @Override
    public void run() {
        angle += 18;
        location.add(0, 0.05, 0);
        armorStand.teleport(location);
        armorStand.setHeadPose(new EulerAngle(0, Math.toRadians(angle), 0));
        location.setY(location.getY() - 0.05);
        armorStand.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 1);
        location.setY(location.getY() + .05);

    }

}



