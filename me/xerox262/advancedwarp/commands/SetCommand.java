package me.xerox262.advancedwarp.commands;

import java.util.HashSet;
import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SetCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public SetCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if ((sender instanceof Player))
    {
      Player player = (Player)sender;
      if (sender.hasPermission("advancedwarp.set"))
      {
        if (args.length > 0)
        {
          int allowedWarps = this.plugin.getWarpAPI().getAllowedWarps(player);
          if (allowedWarps != -1)
          {
            int currentWarps = this.plugin.getWarpAPI().getWarps(player.getUniqueId()).size();
            if (currentWarps >= allowedWarps)
            {
              sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler()
                .getDictionary().getString("Errors.Set.Warp limit reached")));
              return true;
            }
          }
          String warpName = args[0];
          if(warpName.contains(".")) {
        	  sender.sendMessage("地标名称中不能包含 `.` 符号");
        	  return true;
          }
          if (this.plugin.getWarpAPI().warpExists(warpName))
          {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
              this.plugin.getConfigHandler().getDictionary().getString("Errors.Set.Warp already exists")));
            return true;
          }
          UUID uuid = player.getUniqueId();
          Location destination = player.getLocation();
          
          this.plugin.getWarpAPI().createWarp(warpName, destination, uuid);
          sender.sendMessage(
            ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Set"))
            .replace("%X%", Double.valueOf(destination.getX()).toString())
            .replace("%Y%", Double.valueOf(destination.getY()).toString())
            .replace("%Z%", Double.valueOf(destination.getZ()).toString())
            .replace("%Yaw%", Float.valueOf(destination.getYaw()).toString())
            .replace("%Pitch%", Float.valueOf(destination.getPitch()).toString())
            .replace("%Name%", warpName));
        }
        else
        {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Errors.Set.Name not specified")));
        }
      }
      else {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Errors.Set.No permission")));
      }
    }
    else
    {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Errors.Set.Player only")));
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\SetCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */