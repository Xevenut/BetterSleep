package de.Kurfat.Java.Minecraft.BetterSleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin{

	private static Plugin instance;
	
	public static Plugin getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		Bukkit.getPluginManager().registerEvents(new BetterSleep(), this);
	}
	@Override
	public void onDisable() {
		
	}
	
}
