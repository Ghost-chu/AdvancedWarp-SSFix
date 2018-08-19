package me.xerox262.advancedwarp.commands;

import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.Warp;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class RenameWarpCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public RenameWarpCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if (sender.hasPermission("advancedwarp.rename"))
    {
      if (args.length > 0)
      {
        String warpName = args[0];
        if (this.plugin.getWarpAPI().warpExists(warpName))
        {
          if (args.length > 1)
          {
            Warp warp = this.plugin.getWarpAPI().getWarp(warpName);
            if ((sender.hasPermission("advancedwarp.rename.override")) || (((sender instanceof Player)) && 
              (((Player)sender).getUniqueId().equals(warp.getOwner()))))
            {
              String oldName = warp.getName();
              if(warpName.contains(".")) {
            	  sender.sendMessage("新的地标名称中不能包含 `.` 符号");
            	  return true;
              }
              if(oldName.contains(".")) {
            	  sender.sendMessage("旧的地标名称中不能包含 `.` 符号");
            	  return true;
              }
              if ((this.plugin.getWarpAPI().warpExists(args[1])) && (!oldName.equalsIgnoreCase(warpName)))
              {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Errors.Rename.Warp already exists")));
                return true;
              }
              this.plugin.getWarpAPI().renameWarp(warpName, args[1]);
              sender.sendMessage(
                ChatColor.translateAlternateColorCodes('&', 
                this.plugin.getConfigHandler().getDictionary().getString("Renamed"))
                .replace("%oldName%", oldName).replace("%newName%", args[1]));
            }
            else
            {
              sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler()
                .getDictionary().getString("Errors.Rename.Not warp owner")));
            }
          }
          else
          {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler()
              .getDictionary().getString("Errors.Rename.Did not enter new name")));
          }
        }
        else {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Errors.Rename.Warp does not exist")));
        }
      }
      else
      {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary()
          .getString("Errors.Rename.Did not enter warp to rename")));
      }
    }
    else {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
        this.plugin.getConfigHandler().getDictionary().getString("Errors.Rename.No permission")));
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\RenameWarpCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */