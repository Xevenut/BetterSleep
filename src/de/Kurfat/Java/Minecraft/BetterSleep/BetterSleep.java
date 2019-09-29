package de.Kurfat.Java.Minecraft.BetterSleep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterSleep extends JavaPlugin implements Listener{

	public static BetterSleep INSTANCE;
	
	private Map<String, BetterSleepWorld> worlds = new HashMap<>();
	
	public BetterSleep(){
		INSTANCE = this;
	}
	
	public List<UUID> getSleepingPlayersByWorld(World world) {
		return worlds.containsKey(world.getName()) == false ? null : new ArrayList<UUID>(worlds.get(world.getName()).sleep);
	}
	
	@Override
	public void onEnable() {
		for(World world : Bukkit.getWorlds()) {
			BetterSleepWorld sworld = new BetterSleepWorld(world);
			Bukkit.getPluginManager().registerEvents(sworld, this);
			worlds.put(world.getName(), sworld);
		}
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	@Override
	public void onDisable() {}
	
	@EventHandler
	public void onWorldDelete(WorldUnloadEvent event) {
		String name = event.getWorld().getName();
		if(worlds.containsKey(name) == false) return;
		HandlerList.unregisterAll(worlds.get(name));
		worlds.remove(name);
	}
	@EventHandler
	public void onWorldCreate(WorldLoadEvent event) {
		BetterSleepWorld sworld = new BetterSleepWorld(event.getWorld());
		Bukkit.getPluginManager().registerEvents(sworld, this);
		worlds.put(event.getWorld().getName(), sworld);
	}
}
