package me.xerox262.advancedwarp.commands;

import java.util.HashSet;
import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LookupCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public LookupCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if (sender.hasPermission("advancedwarp.lookup"))
    {
      UUID lookup;
      if ((args.length > 0) && (sender.hasPermission("advancedwarp.lookup.others")))
      {
        OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
        lookup = op.getUniqueId();
      }
      else
      {
        if ((sender instanceof Player))
        {
          lookup = ((Player)sender).getUniqueId();
        }
        else
        {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Errors.Lookup.Player not specified")));
          return true;
        }
      }
      String message = "";
      OfflinePlayer player = Bukkit.getOfflinePlayer(lookup);
      HashSet<Warp> warps = this.plugin.getWarpAPI().getWarps(lookup);
      int warpLimit = 0;
      if (player.isOnline()) {
        warpLimit = this.plugin.getWarpAPI().getAllowedWarps((Player)player);
      }
      sender.sendMessage(
        ChatColor.translateAlternateColorCodes('&', 
        this.plugin.getConfigHandler().getDictionary().getString("Lookup.Header"))
        .replace("%warpLimit%", warpLimit != -1 ? Integer.valueOf(warpLimit).toString() : 
        this.plugin.getConfigHandler().getDictionary().getString("Lookup.No limit"))
        .replace("%currentWarps%", String.valueOf(warps.size()))
        .replace("%name%", player.getName()));
      for (Warp warp : warps) {
        message = 
        
          message + ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Lookup.Warp")).replace("%warpX%", Double.valueOf(warp.getDestination().getX()).toString()).replace("%warpY%", Double.valueOf(warp.getDestination().getX()).toString()).replace("%warpZ%", Double.valueOf(warp.getDestination().getX()).toString()).replace("%warpWorld%", Double.valueOf(warp.getDestination().getX()).toString()).replace("%warpYaw%", Double.valueOf(warp.getDestination().getX()).toString()).replace("%warpPitch%", Double.valueOf(warp.getDestination().getX()).toString()).replace("%warp%", warp.getName()) + ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Lookup.Separator"));
      }
      if (message.equalsIgnoreCase(""))
      {
        sender.sendMessage(
          ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary()
          .getString("Errors.Lookup.Player has no warps"))
          .replace("%player%", Bukkit.getOfflinePlayer(lookup).getName()));
      }
      else
      {
        message = message.substring(0, message.lastIndexOf(ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Lookup.Separator"))));
        sender.sendMessage(message);
      }
    }
    else
    {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
        this.plugin.getConfigHandler().getDictionary().getString("Errors.Lookup.No permission")));
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\LookupCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */