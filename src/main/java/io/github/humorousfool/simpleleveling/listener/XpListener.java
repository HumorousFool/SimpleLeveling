package io.github.humorousfool.simpleleveling.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;

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
}
