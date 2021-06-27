package io.github.humorousfool.simpleleveling.listener;

import io.github.humorousfool.simpleleveling.SimpleLeveling;
import io.github.humorousfool.simpleleveling.config.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCraft(CraftItemEvent event)
    {
        if(event.isCancelled() ||  event.getWhoClicked().getType() != EntityType.PLAYER) return;
        Player player = (Player) event.getWhoClicked();

        if(Config.XpPerItemCrafted.containsKey(event.getCurrentItem().getType()))
        {
            SimpleLeveling.getInstance().addXp(player, Config.XpPerItemCrafted.get(event.getCurrentItem().getType()));
        }
        else if(Config.DefaultCraftingXp != 0)
        {
            SimpleLeveling.getInstance().addXp(player, Config.DefaultCraftingXp);
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent event)
    {
        if(event.isCancelled() || event.getState() != PlayerFishEvent.State.CAUGHT_ENTITY || event.getCaught().getType() != EntityType.DROPPED_ITEM) return;
        ItemStack item = ((Item) event.getCaught()).getItemStack();

        if(Config.XpPerItemFished.containsKey(item.getType()))
        {
            SimpleLeveling.getInstance().addXp(event.getPlayer(), Config.XpPerItemFished.get(item.getType()));
        }
        else if(Config.DefaultCraftingXp != 0)
        {
            SimpleLeveling.getInstance().addXp(event.getPlayer(), Config.DefaultFishingXp);
        }
    }
}
