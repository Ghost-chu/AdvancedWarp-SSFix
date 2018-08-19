package me.xerox262.advancedwarp.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.Warp;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class WarpListCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public WarpListCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if (sender.hasPermission("advancedwarp.list"))
    {
      int page = 0;
      if (args.length > 0) {
        try
        {
          page = Integer.parseInt(args[0]);
          if (page < 0) {
            page = 0;
          }
        }
        catch (NumberFormatException nfe)
        {
          page = 0;
        }
      }
      int start = page * this.plugin.getConfig().getInt("Warps per page");int end = start + this.plugin.getConfig().getInt("Warps per page");
      
      HashSet<Warp> warps = this.plugin.getWarpAPI().getAllWarps();
      if ((warps.size() <= start) || (warps.size() == 0))
      {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Errors.Warp List.Not enough warps")));
        return true;
      }
      int counter = 0;
      Iterator<Warp> it = warps.iterator();
      String message = "";
      while (start <= end)
      {
        if (!it.hasNext()) {
          break;
        }
        Warp warp = (Warp)it.next();
        if (counter >= start)
        {
          OfflinePlayer op = Bukkit.getOfflinePlayer(warp.getOwner());
          message = message + ChatColor.translateAlternateColorCodes('&', 
            new StringBuilder(
            
            String.valueOf((op.isOnline()) || (op.getUniqueId().equals(AdvancedWarp.consoleUUID)) ? this.plugin.getConfigHandler().getDictionary().getString("Warp List.Owner online") : this.plugin.getConfigHandler().getDictionary().getString("Warp List.Owner offline")))
            .append(this.plugin.getConfigHandler().getDictionary().getString("Warp List.Separator")).toString())
            .replace("%owner%", op.getName())
            .replace("%warpX%", Double.valueOf(warp.getDestination().getX()).toString())
            .replace("%warpY%", Double.valueOf(warp.getDestination().getX()).toString())
            .replace("%warpZ%", Double.valueOf(warp.getDestination().getX()).toString())
            .replace("%warpWorld%", Double.valueOf(warp.getDestination().getX()).toString())
            .replace("%warpYaw%", Double.valueOf(warp.getDestination().getX()).toString())
            .replace("%warpPitch%", Double.valueOf(warp.getDestination().getX()).toString())
            .replace("%warp%", warp.getName());
        }
        counter++;
      }
      message = 
      
        message.substring(0, message.lastIndexOf(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Warp List.Separator")))).trim();
      
      sender.sendMessage(message);
    }
    else
    {
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
        this.plugin.getConfigHandler().getDictionary().getString("Errors.Warp List.No permission")));
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\WarpListCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */