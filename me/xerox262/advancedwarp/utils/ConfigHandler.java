package me.xerox262.advancedwarp.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Logger;
import me.xerox262.advancedwarp.AdvancedWarp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigHandler
{
  private AdvancedWarp plugin;
  private YamlConfiguration dictionary;
  
  public ConfigHandler(AdvancedWarp plugin)
  {
    this.plugin = plugin;
    loadDictionary();
    loadWarps();
  }
  
  public YamlConfiguration getDictionary()
  {
    return this.dictionary;
  }
  
  public void loadDictionary()
  {
    saveDefaultDictionary();
    this.dictionary = YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder(), "dictionary.yml"));
  }
  
  public void saveDefaultDictionary()
  {
    this.plugin.saveResource("dictionary.yml", false);
  }
  
  public void saveWarps()
  {
    HashSet<Warp> warps = this.plugin.getWarpAPI().getAllWarps();
    
    YamlConfiguration config = new YamlConfiguration();
    for (Warp warp : warps)
    {
      config.set(warp.getName() + ".World", warp.getDestination().getWorld().getName());
      config.set(warp.getName() + ".X", Double.valueOf(warp.getDestination().getX()).toString().replace(".", "_"));
      config.set(warp.getName() + ".Y", Double.valueOf(warp.getDestination().getY()).toString().replace(".", "_"));
      config.set(warp.getName() + ".Z", Double.valueOf(warp.getDestination().getZ()).toString().replace(".", "_"));
      config.set(warp.getName() + ".Yaw", Float.valueOf(warp.getDestination().getYaw()).toString().replace(".", "_"));
      config.set(warp.getName() + ".Pitch", 
        Float.valueOf(warp.getDestination().getPitch()).toString().replace(".", "_"));
      config.set(warp.getName() + ".Owner", warp.getOwner().toString());
    }
    try
    {
      config.save(new File(this.plugin.getDataFolder(), "warps.yml"));
    }
    catch (IOException e)
    {
      this.plugin.getLogger().warning("Error saving warps");
    }
  }
  
  public void loadWarps()
  {
    HashSet<Warp> warps = new HashSet<Warp>();
    YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(this.plugin.getDataFolder(), "warps.yml"));
    for (String configSection : config.getKeys(false))
    {
      double x = Double.parseDouble(config.getString(configSection + ".X").replace("_", "."));
      double y = Double.parseDouble(config.getString(configSection + ".Y").replace("_", "."));
      double z = Double.parseDouble(config.getString(configSection + ".Z").replace("_", "."));
      float yaw = Float.parseFloat(config.getString(configSection + ".Yaw").replace("_", "."));
      float pitch = Float.parseFloat(config.getString(configSection + ".Pitch").replace("_", "."));
      World world = Bukkit.getWorld(config.getString(configSection + ".World"));
      
      UUID owner = UUID.fromString(config.getString(configSection + ".Owner"));
      Location destination = new Location(world, x, y, z, yaw, pitch);
      
      warps.add(new Warp(configSection, destination, owner));
    }
    this.plugin.getWarpAPI().setWarps(warps);
  }
}