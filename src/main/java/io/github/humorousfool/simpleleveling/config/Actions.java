package io.github.humorousfool.simpleleveling.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class Actions
{
    public static HashMap<Integer, List<String>> actions = new HashMap<>();

    public static void update(FileConfiguration file)
    {
        for(String key : file.getKeys(false))
        {
            int level = Integer.parseInt(key);
            actions.put(level, file.getStringList(key));
        }
    }

    public static void runCommands(int level, String target)
    {
        if(!actions.containsKey(level)) return;

        for(String command : actions.get(level))
        {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replace("<player>", target));
        }
    }
}
