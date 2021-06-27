package io.github.humorousfool.simpleleveling.listener;

import io.github.humorousfool.simpleleveling.SimpleLeveling;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoadingListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event)
    {
        SimpleLeveling.getInstance().loadPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event)
    {
        SimpleLeveling.getInstance().savePlayer(event.getPlayer());
        SimpleLeveling.getInstance().unloadPlayer(event.getPlayer());
    }
}
