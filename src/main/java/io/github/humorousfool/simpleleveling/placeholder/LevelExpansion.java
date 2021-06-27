package io.github.humorousfool.simpleleveling.placeholder;

import io.github.humorousfool.simpleleveling.Level;
import io.github.humorousfool.simpleleveling.SimpleLeveling;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelExpansion extends PlaceholderExpansion
{
    @Override
    public boolean canRegister()
    {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "simpleleveling";
    }

    @Override
    public @NotNull String getAuthor() {
        return "HumorousFool";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier)
    {
        if(identifier.equals("level"))
        {
            if(player.isOnline())
            {
                Player online = (Player) player;
                Level level = SimpleLeveling.getInstance().getLevel(online);
                if(level == null)
                    return "0";
                return Integer.toString(level.level);
            }

            Level level = SimpleLeveling.getInstance().getOfflineLevel(player.getUniqueId());
            if(level == null)
                return "0";
            return Integer.toString(level.level);
        }
        else if(identifier.equals("xp"))
        {
            if (player.isOnline()) {
                Player online = (Player) player;
                Level level = SimpleLeveling.getInstance().getLevel(online);
                if (level == null)
                    return "0";
                return Integer.toString(level.xp);
            }

            Level level = SimpleLeveling.getInstance().getOfflineLevel(player.getUniqueId());
            if (level == null)
                return "0";
            return Integer.toString(level.xp);
        }

        return null;
    }
}
