package io.github.humorousfool.simpleleveling.listener;

import io.github.humorousfool.simpleleveling.Level;
import io.github.humorousfool.simpleleveling.SimpleLeveling;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class XpListener implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onKill(EntityDeathEvent event)
    {
        event.setDroppedExp(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent event)
    {
        event.setExpToDrop(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockExp(BlockExpEvent event)
    {
        event.setExpToDrop(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExpBottle(ExpBottleEvent event)
    {
        event.setExperience(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFurnaceExtract(FurnaceExtractEvent event)
    {
        event.setExpToDrop(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreed(EntityBreedEvent event)
    {
        event.setExperience(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFish(PlayerFishEvent event)
    {
        event.setExpToDrop(0);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisenchant(PlayerExpChangeEvent event)
    {
        Level l = SimpleLeveling.getInstance().getLevel(event.getPlayer());

        if(l != null)
        {
            if(event.getPlayer().getLevel() != l.level || event.getPlayer().getExp() != (float) l.xp / (float) l.getMaxXp())
            {
                event.getPlayer().setLevel(l.level);
                event.getPlayer().setExp((float) l.xp / (float) l.getMaxXp());
                event.setAmount(0);
            }
        }

        event.setAmount(0);
    }
}
