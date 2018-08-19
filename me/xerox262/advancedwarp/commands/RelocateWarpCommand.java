package me.xerox262.advancedwarp.commands;

import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.Warp;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RelocateWarpCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public RelocateWarpCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if ((sender instanceof Player))
    {
      Player player = (Player)sender;
      if (sender.hasPermission("advancedwarp.relocate"))
      {
        if (args.length > 0)
        {
          String warpName = args[0];
          if (this.plugin.getWarpAPI().warpExists(warpName))
          {
            Warp warp = this.plugin.getWarpAPI().getWarp(warpName);
            if ((sender.hasPermission("advancedwarp.relocate.override")) || (((sender instanceof Player)) && 
              (((Player)sender).getUniqueId().equals(warp.getOwner()))))
            {
              sender.sendMessage(
              
                ChatColor.translateAlternateColorCodes('&', 
                this.plugin.getConfigHandler().getDictionary().getString("Relocated"))
                .replace("%oldX%", Double.valueOf(warp.getDestination().getX()).toString())
                .replace("%oldY%", Double.valueOf(warp.getDestination().getY()).toString())
                .replace("%oldZ%", Double.valueOf(warp.getDestination().getZ()).toString())
                .replace("%newX%", Double.valueOf(player.getLocation().getX()).toString())
                .replace("%newY%", Double.valueOf(player.getLocation().getY()).toString())
                .replace("%newZ%", Double.valueOf(player.getLocation().getZ()).toString())
                .replace("%oldYaw%", Float.valueOf(warp.getDestination().getYaw()).toString())
                .replace("%oldPitch%", 
                Float.valueOf(warp.getDestination().getPitch()).toString())
                .replace("%newYaw%", Float.valueOf(player.getLocation().getYaw()).toString())
                .replace("%newPitch%", Float.valueOf(player.getLocation().getPitch()).toString())
                .replace("%oldWorld%", warp.getDestination().getWorld().getName())
                .replace("%newWorld%", player.getLocation().getWorld().getName())
                .replace("%name%", warp.getName()));
              this.plugin.getWarpAPI().relocateWarp(warpName, player.getLocation());
            }
            else
            {
              sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler()
                .getDictionary().getString("Errors.Relocate.Not warp owner")));
            }
          }
          else
          {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler()
              .getDictionary().getString("Errors.Relocate.Warp does not exist")));
          }
        }
        else
        {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Errors.Relocate.Warp not specified")));
        }
      }
      else {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Errors.Relocate.No permission")));
      }
    }
    else
    {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
        this.plugin.getConfigHandler().getDictionary().getString("Errors.Relocate.Player only")));
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\RelocateWarpCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */