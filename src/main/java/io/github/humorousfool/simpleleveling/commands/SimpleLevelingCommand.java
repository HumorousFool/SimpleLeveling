package io.github.humorousfool.simpleleveling.commands;

import io.github.humorousfool.simpleleveling.Level;
import io.github.humorousfool.simpleleveling.SimpleLeveling;
import io.github.humorousfool.simpleleveling.localisation.I18nSupport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleLevelingCommand implements CommandExecutor, TabCompleter
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(args.length == 0)
        {
            getCommand(sender);
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if(player == null || !player.isOnline())
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Invalid Player"));
            return true;
        }

        else if(args.length == 1)
        {
            getCommand(sender, player, true);
        }
        else
        {
            if(args[1].equalsIgnoreCase("addXp"))
                addCommand(sender, args);
            else if(args[1].equalsIgnoreCase("reset"))
                resetCommand(sender, args[0]);
            else
                sender.sendMessage(ChatColor.GREEN + I18nSupport.getInternationalisedString("Valid Subcommands") + ChatColor.DARK_AQUA + " get, addXp, reset");
        }

        return true;
    }

    private void getCommand(CommandSender sender)
    {
        if(!sender.hasPermission("simpleleveling.commands.get"))
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Insufficient Permissions"));
            return;
        }
        if(sender instanceof Player)
            getCommand(sender, (Player) sender, false);
        else
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Must Be Player"));
    }

    private void getCommand(CommandSender sender, Player player, boolean other)
    {
        if(other && !sender.hasPermission("simpleleveling.commands.get.other"))
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Insufficient Permissions"));
            return;
        }

        Level level = SimpleLeveling.getInstance().getLevel(player);

        sender.sendMessage(ChatColor.GREEN + I18nSupport.getInternationalisedString("Level") + " " + ChatColor.DARK_AQUA + level.level);
        sender.sendMessage(ChatColor.GREEN + I18nSupport.getInternationalisedString("Xp") + " " +
                ChatColor.DARK_AQUA + level.xp + ChatColor.GREEN + "/" + ChatColor.AQUA + level.getMaxXp());
    }

    private void addCommand(CommandSender sender, String[] args)
    {
        if(!sender.hasPermission("simpleleveling.commands.add"))
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Insufficient Permissions"));
            return;
        }
        if(args.length < 3)
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Invalid Amount"));
            return;
        }

        int amount;
        try
        {
            amount = Integer.parseInt(args[2]);
        }
        catch (Exception e)
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Invalid Amount"));
            return;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if(player == null || !player.isOnline())
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Invalid Player"));
            return;
        }

        SimpleLeveling.getInstance().addXp(player, amount);
        sender.sendMessage(ChatColor.GREEN + I18nSupport.getInternationalisedString("Gave Xp")
                .replace("<amount>", ChatColor.DARK_AQUA + "" + amount + " XP" + ChatColor.GREEN)
                .replace("<player>", player.getName()));
    }

    private void resetCommand(CommandSender sender, String name)
    {
        if(!sender.hasPermission("simpleleveling.commands.reset"))
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Insufficient Permissions"));
            return;
        }

        Player player = Bukkit.getPlayer(name);
        if(player == null || !player.isOnline())
        {
            sender.sendMessage(ChatColor.RED + I18nSupport.getInternationalisedString("Invalid Player"));
            return;
        }
        
        SimpleLeveling.getInstance().resetPlayer(player);
        sender.sendMessage(ChatColor.GREEN + I18nSupport.getInternationalisedString("Reset Player")
                .replace("<player>", ChatColor.AQUA + player.getName() + ChatColor.GREEN));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        if(args.length < 2)
        {
            List<String> players = new ArrayList<>();

            for(Player player : Bukkit.getServer().getOnlinePlayers())
            {
                players.add(player.getName());
            }
            return players;
        }
        else if(args.length == 2)
        {
            List<String> subCommands = new ArrayList<>();
            subCommands.add("addXp");
            subCommands.add("reset");
            return subCommands;
        }

        return Collections.emptyList();
    }
}
