package io.github.humorousfool.simpleleveling;

import io.github.humorousfool.simpleleveling.commands.SimpleLevelingCommand;
import io.github.humorousfool.simpleleveling.config.Actions;
import io.github.humorousfool.simpleleveling.config.Config;
import io.github.humorousfool.simpleleveling.listener.*;
import io.github.humorousfool.simpleleveling.localisation.I18nSupport;
import io.github.humorousfool.simpleleveling.placeholder.LevelExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class SimpleLeveling extends JavaPlugin
{
    private static SimpleLeveling instance;

    File levelsFile;
    YamlConfiguration levelsYaml;
    private final HashMap<Player, Level> levels = new HashMap<>();

    private LevelExpansion expansion;

    @Override
    public void onEnable()
    {
        instance = this;

        saveDefaultConfig();
        Config.update(getConfig());

        String[] localisations = {"en", "fr"};
        for (String s : localisations) {
            if (!new File(getDataFolder()
                    + "/localisation/simplelevelinglang_" + s + ".properties").exists()) {
                this.saveResource("localisation/simplelevelinglang_" + s + ".properties", false);
            }
        }
        I18nSupport.init();

        File actionsFile = new File(getDataFolder(), "actions.yml");
        if (!actionsFile.exists()) {
            this.saveResource("actions.yml", false);
        }
        Actions.update(YamlConfiguration.loadConfiguration(actionsFile));

        levels.clear();
        levelsFile = new File(getDataFolder(), "levels.yml");
        if(!levelsFile.exists())
        {
            try {
                levelsFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        levelsYaml = YamlConfiguration.loadConfiguration(levelsFile);

        for(Player player : getServer().getOnlinePlayers())
        {
            loadPlayer(player);
        }

        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null && getServer().getPluginManager().getPlugin("PlaceHolderAPI").isEnabled())
        {
            getLogger().log(java.util.logging.Level.INFO, "Found PlaceholderAPI! Enabling Integration!");
            expansion = new LevelExpansion();
            expansion.register();
        }

        getCommand("simpleleveling").setExecutor(new SimpleLevelingCommand());

        getServer().getPluginManager().registerEvents(new LoadingListener(), this);
        if(Config.OverrideVanillaXp)
            getServer().getPluginManager().registerEvents(new XpListener(), this);
        if(Config.DefaultBlockBreakXp != 0 || !Config.XpPerBlockBreak.isEmpty() ||
                Config.DefaultBlockPlaceXp != 0 || !Config.XpPerBlockPlace.isEmpty())
            getServer().getPluginManager().registerEvents(new BlockListener(), this);
        if(Config.DefaultKillXp != 0 || !Config.XpPerEntityKill.isEmpty() ||
                Config.DefaultBreedXp != 0 || !Config.XpPerEntityBreed.isEmpty() ||
                Config.DefaultTameXp != 0 || !Config.XpPerEntityTame.isEmpty())
            getServer().getPluginManager().registerEvents(new EntityListener(), this);
        if(Config.DefaultCraftingXp != 0 || !Config.XpPerItemCrafted.isEmpty() ||
                Config.DefaultFishingXp != 0 || !Config.XpPerItemFished.isEmpty())
            getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable()
    {
        expansion.unregister();
        saveAll();
        instance = null;
    }

    public void addXp(Player player, int amount)
    {
        if(player == null || !player.isOnline() || !levels.containsKey(player)) return;

        Level level = levels.get(player);
        level.addXp(player, amount);
        levels.replace(player, level);
    }

    public Level getLevel(Player player)
    {
        return levels.getOrDefault(player, null);
    }

    public Level getOfflineLevel(UUID id)
    {
        List<Integer> l = levelsYaml.getIntegerList(id.toString());

        if(l.isEmpty())
            return null;
        else
            return new Level(l.get(0), l.get(1));
    }

    public void loadPlayer(Player player)
    {
        for(String key : levelsYaml.getValues(false).keySet())
        {
            if(player.getUniqueId().toString().equals(key))
            {
                List<Integer> list = levelsYaml.getIntegerList(key);
                Level level = new Level(list.get(0), list.get(1));
                levels.put(player, level);
            }
        }

        if(!levels.containsKey(player))
        {
            levels.put(player, new Level(Config.StartLevel, 0));
            savePlayer(player);
        }
    }

    public void unloadPlayer(Player player)
    {
        levels.remove(player);
    }

    public void savePlayer(Player player)
    {
        if(!levels.containsKey(player)) return;
        Level l = levels.get(player);
        List<Integer> data = new ArrayList<>();
        data.add(l.level);
        data.add(l.xp);
        levelsYaml.set(player.getUniqueId().toString(), data);

        try {
            levelsYaml.save(levelsFile);
        } catch (IOException e) {
            getLogger().log(java.util.logging.Level.SEVERE, "Failed to save levels!");
            e.printStackTrace();
        }
    }

    public void saveAll()
    {
        for(Player player : levels.keySet())
        {
            Level l = levels.get(player);
            List<Integer> data = new ArrayList<>();
            data.add(l.level);
            data.add(l.xp);
            levelsYaml.set(player.getUniqueId().toString(), data);
        }

        try {
            levelsYaml.save(levelsFile);
        } catch (IOException e) {
            getLogger().log(java.util.logging.Level.SEVERE, "Failed to save levels!");
            e.printStackTrace();
        }
    }

    public void resetPlayer(Player player)
    {
        levels.remove(player);
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(0);
        levelsYaml.set(player.getUniqueId().toString(), data);
        levels.put(player, new Level(1, 0));
        player.setLevel(1);
        player.setExp(0f);

        try {
            levelsYaml.save(levelsFile);
        } catch (IOException e) {
            getLogger().log(java.util.logging.Level.SEVERE, "Failed to save levels!");
            e.printStackTrace();
        }
    }

    public static SimpleLeveling getInstance()
    {
        return instance;
    }
}
