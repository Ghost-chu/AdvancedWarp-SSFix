package me.xerox262.advancedwarp.utils;

import java.util.HashSet;
import java.util.UUID;
import me.xerox262.advancedwarp.AdvancedWarp;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class WarpAPI
{
  private HashSet<Warp> warps;
  
  public WarpAPI(AdvancedWarp plugin)
  {
    this.warps = new HashSet();
  }
  
  public void createWarp(String warpName, Location destination, UUID owner)
  {
    Warp warp = new Warp(warpName, destination, owner);
    this.warps.add(warp);
  }
  
  public boolean warpExists(String warpName)
  {
    for (Warp w : this.warps) {
      if (w.getName().equalsIgnoreCase(warpName)) {
        return true;
      }
    }
    return false;
  }
  
  public Warp getWarp(String warpName)
  {
    for (Warp w : this.warps) {
      if (w.getName().equalsIgnoreCase(warpName)) {
        return w;
      }
    }
    return null;
  }
  
  public HashSet<Warp> getWarps(UUID owner)
  {
    HashSet<Warp> warpsToReturn = new HashSet();
    for (Warp w : this.warps) {
      if (w.getOwner().equals(owner)) {
        warpsToReturn.add(w);
      }
    }
    return warpsToReturn;
  }
  
  public void renameWarp(String warpName, String newName)
  {
    getWarp(warpName).setName(newName);
  }
  
  public void relocateWarp(String warpName, Location destination)
  {
    getWarp(warpName).setDestination(destination);
  }
  
  public void updateOwner(String warpName, UUID owner)
  {
    getWarp(warpName).setOwner(owner);
  }
  
  public void deleteWarp(String warpName)
  {
    this.warps.remove(getWarp(warpName));
  }
  
  public void teleportToWarp(Entity entity, Warp warp)
  {
    if (entity.getVehicle() != null)
    {
      teleportToWarp(entity.getVehicle(), warp);
    }
    else
    {
      Entity passenger = entity.getPassenger();
      if (passenger != null)
      {
        entity.eject();
        teleportToWarp(passenger, warp);
      }
      entity.teleport(warp.getDestination());
      if (passenger != null) {
        entity.setPassenger(passenger);
      }
    }
  }
  
  public int getAllowedWarps(Player player)
  {
    if ((player.hasPermission("advancedwarp.set.amount.infinite")) || 
      (player.hasPermission("advancedwarp.set.amount.-1"))) {
      return -1;
    }
    int allowedWarps = 0;
    for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
      if (info.getPermission().startsWith("advancedwarp.set.amount.")) {
        try
        {
          int temp = Integer.parseInt(info.getPermission().replace("advancedwarp.set.amount.", ""));
          if (temp > allowedWarps) {
            allowedWarps = temp;
          }
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
    }
    return allowedWarps;
  }
  
  public HashSet<Warp> getAllWarps()
  {
    return this.warps;
  }
  
  public void setWarps(HashSet<Warp> warps)
  {
    if (warps != null) {
      this.warps = warps;
    }
  }
}
