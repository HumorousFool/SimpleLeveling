package io.github.humorousfool.simpleleveling;

import io.github.humorousfool.simpleleveling.config.Actions;
import io.github.humorousfool.simpleleveling.config.Config;
import io.github.humorousfool.simpleleveling.localisation.I18nSupport;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Level
{
    public int level;
    public int xp;

    public Level(int baseLevel, int baseXp)
    {
        this.level = baseLevel;
        this.xp = baseXp;
    }

    public int getMaxXp()
    {
        double maxXp;
        if(Config.XpMultiplier != 0)
            maxXp = Config.XpBaseAmount + (Config.XpBaseAmountPerLevel * level) * (Config.XpMultiplier * level);
        else
            maxXp = Config.XpBaseAmount + (Config.XpBaseAmountPerLevel * level);

        return (int) maxXp;
    }

    public void addXp(Player player, int amount)
    {
        while (amount > 0)
        {
            if(level >= Config.MaxLevel) return;

            double maxXp = getMaxXp();
            int totalXp = xp + amount;

            if(totalXp < maxXp)
            {
                xp += amount;
                break;
            }
            else
            {
                level += 1;

                if(Config.OverrideVanillaXp)
                    player.setLevel(level);
                player.sendMessage(ChatColor.GREEN + I18nSupport.getInternationalisedString("Level Up") + " " + ChatColor.DARK_AQUA + level + ChatColor.GREEN + "!");
                Actions.runCommands(level, player.getName());
                if(Config.BroadcastMessages.containsKey(level))
                    player.getServer().broadcastMessage(Config.BroadcastMessages.get(level)
                            .replace("<player>", player.getName()).replace("<level>", Integer.toString(level)));

                amount = totalXp - (int) maxXp;
            }
        }

        if(Config.OverrideVanillaXp)
        {
            player.setLevel(level);
            player.setExp((float) xp / (float) getMaxXp());
        }
    }
}
