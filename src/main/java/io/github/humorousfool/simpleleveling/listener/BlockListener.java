package io.github.humorousfool.simpleleveling.listener;

import io.github.humorousfool.simpleleveling.SimpleLeveling;
import io.github.humorousfool.simpleleveling.config.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event)
    {
        if(event.isCancelled()) return;

        if(Config.XpPerBlockBreak.containsKey(event.getBlock().getType()))
            SimpleLeveling.getInstance().addXp(event.getPlayer(), Config.XpPerBlockBreak.get(event.getBlock().getType()));
        else if(Config.DefaultBlockBreakXp != 0)
            SimpleLeveling.getInstance().addXp(event.getPlayer(), Config.DefaultBlockBreakXp);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if(event.isCancelled()) return;

        if(Config.XpPerBlockPlace.containsKey(event.getBlock().getType()))
            SimpleLeveling.getInstance().addXp(event.getPlayer(), Config.XpPerBlockPlace.get(event.getBlock().getType()));
        else if(Config.DefaultBlockPlaceXp != 0)
            SimpleLeveling.getInstance().addXp(event.getPlayer(), Config.DefaultBlockPlaceXp);
    }
}
