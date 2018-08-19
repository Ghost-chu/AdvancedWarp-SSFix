package me.xerox262.advancedwarp.utils;

import java.util.UUID;
import org.bukkit.Location;

public class Warp
{
  private UUID owner;
  private String name;
  private Location destination;
  
  public Warp(String name, Location destination, UUID owner)
  {
    this.name = name;
    this.owner = owner;
    this.destination = destination;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public UUID getOwner()
  {
    return this.owner;
  }
  
  public Location getDestination()
  {
    return this.destination;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public void setDestination(Location destination)
  {
    this.destination = destination;
  }
  
  public void setOwner(UUID owner)
  {
    this.owner = owner;
  }
}