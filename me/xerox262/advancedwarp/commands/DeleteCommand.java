package me.xerox262.advancedwarp.commands;

import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.Warp;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class DeleteCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public DeleteCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if (sender.hasPermission("advancedwarp.delete"))
    {
      if (args.length > 0)
      {
        String warpName = args[0];
        if (this.plugin.getWarpAPI().warpExists(warpName))
        {
          Warp warp = this.plugin.getWarpAPI().getWarp(warpName);
          if ((sender.hasPermission("advancedwarp.delete.override")) || (
            ((sender instanceof Player)) && (((Player)sender).getUniqueId().equals(warp.getOwner()))))
          {
            this.plugin.getWarpAPI().deleteWarp(warpName);
            
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
              this.plugin.getConfigHandler().getDictionary().getString("Deleted"))
              .replace("%name%", warp.getName())
              .replace("%owner%", Bukkit.getOfflinePlayer(warp.getOwner()).getName()));
          }
          else
          {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
              this.plugin.getConfigHandler().getDictionary().getString("Errors.Delete.Not warp owner")));
          }
        }
        else
        {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Errors.Delete.Warp does not exist")));
        }
      }
      else
      {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Errors.Delete.Warp not specified")));
      }
    }
    else {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
        this.plugin.getConfigHandler().getDictionary().getString("Errors.Delete.No permission")));
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\DeleteCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */