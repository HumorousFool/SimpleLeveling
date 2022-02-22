package io.github.humorousfool.simpleleveling.config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.HashMap;

public class Config
{
    public static String Locale = "en";
    public static boolean OverrideVanillaXp = true;

    public static HashMap<Integer, String> BroadcastMessages = new HashMap<>();

    public static int StartLevel = 1;
    public static int MaxLevel = 100;

    public static int XpBaseAmount = 0;
    public static int XpBaseAmountPerLevel = 100;
    public static double XpMultiplier = 1D;

    public static int RemoveXpOnDeathAfterLevel = 10;
    public static int RemoveXpMaxAmount = 1000;

    public static int DefaultBlockBreakXp = 0;
    public static HashMap<Material, Integer> XpPerBlockBreak = new HashMap<>();

    public static int DefaultBlockPlaceXp = 0;
    public static HashMap<Material, Integer> XpPerBlockPlace = new HashMap<>();

    public static int DefaultCraftingXp = 0;
    public static HashMap<Material, Integer> XpPerItemCrafted = new HashMap<>();

    public static int DefaultKillXp = 10;
    public static HashMap<EntityType, Integer> XpPerEntityKill = new HashMap<>();

    public static int DefaultBreedXp = 20;
    public static HashMap<EntityType, Integer> XpPerEntityBreed = new HashMap<>();

    public static int DefaultTameXp = 20;
    public static HashMap<EntityType, Integer> XpPerEntityTame = new HashMap<>();

    public static int DefaultFishingXp = 10;
    public static HashMap<Material, Integer> XpPerItemFished = new HashMap<>();

    public static void update(FileConfiguration file)
    {
        Locale = file.getString("Locale");

        OverrideVanillaXp = file.getBoolean("OverrideVanillaXp", true);

        BroadcastMessages = getBroadcastMap((MemorySection) file.get("BroadcastMessages"));

        StartLevel = file.getInt("StartLevel", 1);
        MaxLevel = file.getInt("MaxLevel", 100);

        XpBaseAmount = file.getInt("XpBaseAmountPerLevel", 100);
        XpBaseAmountPerLevel = file.getInt("XpBaseAmountPerLevel", 100);
        XpMultiplier = file.getDouble("XpMultiplier", 1D);

        RemoveXpOnDeathAfterLevel = file.getInt("RemoveXpOnDeathAfterLevel", 10);
        RemoveXpMaxAmount = file.getInt("RemoveXpMAxAmount", 1000);

        DefaultBlockBreakXp = file.getInt("DefaultBlockBreakXp", 0);
        XpPerBlockBreak = getMaterialMap((MemorySection) file.get("XpPerBlockBreak"));

        DefaultBlockPlaceXp = file.getInt("DefaultBlockPlaceXp", 0);
        XpPerBlockPlace = getMaterialMap((MemorySection) file.get("XpPerBlockPlace"));

        DefaultCraftingXp = file.getInt("DefaultCraftingXp", 0);
        XpPerItemCrafted = getMaterialMap((MemorySection) file.get("XpPerItemCrafted"));

        DefaultKillXp = file.getInt("DefaultKillXp", 10);
        XpPerEntityKill = getEntityMap((MemorySection) file.get("XpPerEntityKill"));

        DefaultBreedXp = file.getInt("DefaultBreedXp", 0);
        XpPerEntityBreed = getEntityMap((MemorySection) file.get("XpPerEntityBreed"));

        DefaultTameXp = file.getInt("DefaultTameXp", 0);
        XpPerEntityTame = getEntityMap((MemorySection) file.get("XpPerEntityTame"));

        DefaultFishingXp = file.getInt("DefaultFishingXp", 10);
        XpPerItemFished = getMaterialMap((MemorySection) file.get("XpPerItemFished"));
    }

    private static HashMap<Material, Integer> getMaterialMap(MemorySection memorySection)
    {
        HashMap<Material, Integer> map = new HashMap<>();

        if(memorySection == null)
            return map;

        for(String key : memorySection.getKeys(false))
        {
            Material material = Material.matchMaterial(key);
            map.put(material, memorySection.getInt(key));
        }

        return map;
    }

    private static HashMap<EntityType, Integer> getEntityMap(MemorySection memorySection)
    {
        HashMap<EntityType, Integer> map = new HashMap<>();

        if(memorySection == null)
            return map;

        for(String key : memorySection.getKeys(false))
        {
            EntityType entityType = EntityType.valueOf(key);
            map.put(entityType, memorySection.getInt(key));
        }

        return map;
    }

    private static HashMap<Integer, String> getBroadcastMap(MemorySection memorySection)
    {
        HashMap<Integer, String> map = new HashMap<>();

        if(memorySection == null)
            return map;

        for(String key : memorySection.getKeys(false))
        {
            map.put(Integer.parseInt(key), memorySection.getString(key, ChatColor.RED + "Failed to load broadcast message!")
                    .replace("&", "§"));
        }

        return map;
    }
}
