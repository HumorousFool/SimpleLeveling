package io.github.humorousfool.simpleleveling.listener;

import io.github.humorousfool.simpleleveling.SimpleLeveling;
import io.github.humorousfool.simpleleveling.config.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTameEvent;

public class EntityListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityKilled(EntityDeathEvent event)
    {
        if(event.getEntity().getKiller() == null || !event.getEntity().getKiller().isOnline()) return;

        if(Config.XpPerEntityKill.containsKey(event.getEntityType()))
        {
            SimpleLeveling.getInstance().addXp(event.getEntity().getKiller(), Config.XpPerEntityKill.get(event.getEntityType()));
        }
        else if(Config.DefaultKillXp != 0)
        {
            SimpleLeveling.getInstance().addXp(event.getEntity().getKiller(), Config.DefaultKillXp);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityBreed(EntityBreedEvent event)
    {
        if(event.isCancelled() || event.getBreeder() == null || event.getBreeder().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getBreeder();

        if(Config.XpPerEntityBreed.containsKey(event.getEntityType()))
        {
            SimpleLeveling.getInstance().addXp(player, Config.XpPerEntityBreed.get(event.getEntityType()));
        }
        else if(Config.DefaultBreedXp != 0)
        {
            SimpleLeveling.getInstance().addXp(player, Config.DefaultBreedXp);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityTame(EntityTameEvent event)
    {
        if(event.isCancelled() || !(event.getOwner() instanceof Player)) return;
        Player player = (Player) event.getOwner();

        if(Config.XpPerEntityBreed.containsKey(event.getEntityType()))
        {
            SimpleLeveling.getInstance().addXp(player, Config.XpPerEntityBreed.get(event.getEntityType()));
        }
        else if(Config.DefaultBreedXp != 0)
        {
            SimpleLeveling.getInstance().addXp(player, Config.DefaultBreedXp);
        }
    }
}
