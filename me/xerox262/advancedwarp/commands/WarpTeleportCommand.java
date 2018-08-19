package me.xerox262.advancedwarp.commands;

import me.xerox262.advancedwarp.AdvancedWarp;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.Warp;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class WarpTeleportCommand
  implements CommandExecutor
{
  private AdvancedWarp plugin;
  
  public WarpTeleportCommand(AdvancedWarp plugin)
  {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args)
  {
    if (sender.hasPermission("advancedwarp.warp"))
    {
      Player warpee = null;
      if (args.length > 1)
      {
        warpee = Bukkit.getPlayer(args[1]);
        if (warpee != sender)
        {
          if (!sender.hasPermission("advancedwarp.warp.others"))
          {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Errors.WarpTP.No permission to warp others")));
            return true;
          }
        }
        else if (warpee == null)
        {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Errors.WarpTP.Player not online")));
          return true;
        }
      }
      else if (args.length > 0)
      {
        if ((sender instanceof Player))
        {
          warpee = (Player)sender;
        }
        else
        {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
            this.plugin.getConfigHandler().getDictionary().getString("Errors.WarpTP.Console can not warp")));
          return true;
        }
      }
      else
      {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("Errors.WarpTP.Warp not specified")));
        return true;
      }
      String warpName = args[0];
      if (!this.plugin.getWarpAPI().warpExists(warpName))
      {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("Errors.WarpTP.Warp does not exist")));
        return true;
      }
      Warp warp = this.plugin.getWarpAPI().getWarp(warpName);
      this.plugin.getWarpAPI().teleportToWarp(warpee, warp);
      warpee.sendMessage(
        ChatColor.translateAlternateColorCodes('&', this.plugin.getConfigHandler().getDictionary().getString("WarpTP.Warped"))
        .replace("%name%", warp.getName()).replace("%player%", warpee.getName()));
      if (warpee != sender) {
        sender.sendMessage(
          ChatColor.translateAlternateColorCodes('&', 
          this.plugin.getConfigHandler().getDictionary().getString("WarpTP.Warped other"))
          .replace("%name%", warp.getName()).replace("%player%", warpee.getName()));
      }
    }
    return true;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\commands\WarpTeleportCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */