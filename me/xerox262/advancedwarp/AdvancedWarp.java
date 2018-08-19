package me.xerox262.advancedwarp;

import java.util.UUID;
import me.xerox262.advancedwarp.commands.DeleteCommand;
import me.xerox262.advancedwarp.commands.LookupCommand;
import me.xerox262.advancedwarp.commands.RelocateWarpCommand;
import me.xerox262.advancedwarp.commands.RenameWarpCommand;
import me.xerox262.advancedwarp.commands.SetCommand;
import me.xerox262.advancedwarp.commands.WarpListCommand;
import me.xerox262.advancedwarp.commands.WarpTeleportCommand;
import me.xerox262.advancedwarp.utils.ConfigHandler;
import me.xerox262.advancedwarp.utils.WarpAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class AdvancedWarp
  extends JavaPlugin
{
  private WarpAPI warpAPI;
  private ConfigHandler configHandler;
  public static final UUID consoleUUID = UUID.fromString("f78a4d8d-d51b-4b39-98a3-230f2de0c670");
  
  public void onEnable()
  {
    saveDefaultConfig();
    this.warpAPI = new WarpAPI(this);
    this.configHandler = new ConfigHandler(this);
    
    getCommand("set").setExecutor(new SetCommand(this));
    getCommand("delete").setExecutor(new DeleteCommand(this));
    getCommand("rename").setExecutor(new RenameWarpCommand(this));
    getCommand("relocate").setExecutor(new RelocateWarpCommand(this));
    getCommand("warplist").setExecutor(new WarpListCommand(this));
    getCommand("warptp").setExecutor(new WarpTeleportCommand(this));
    getCommand("lookup").setExecutor(new LookupCommand(this));
  }
  
  public void onDisable()
  {
    this.configHandler.saveWarps();
  }
  
  public WarpAPI getWarpAPI()
  {
    return this.warpAPI;
  }
  
  public ConfigHandler getConfigHandler()
  {
    return this.configHandler;
  }
}


/* Location:              D:\Downloads\AdvancedWarp (1).jar!\me\xerox262\advancedwarp\AdvancedWarp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */