package de.Kurfat.Java.Minecraft.BetterSleep;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class BetterSleep implements Listener{

	private Map<String, BetterSleepWorld> worlds = new HashMap<>();
	
	public BetterSleep() {
		for(World world : Bukkit.getWorlds()) {
			BetterSleepWorld sworld = new BetterSleepWorld(world);
			Bukkit.getPluginManager().registerEvents(sworld, Plugin.getInstance());
			worlds.put(world.getName(), sworld);
		}
	}
	
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
		Bukkit.getPluginManager().registerEvents(sworld, Plugin.getInstance());
		worlds.put(event.getWorld().getName(), sworld);
	}
}
