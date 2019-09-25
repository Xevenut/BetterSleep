package de.Kurfat.Java.Minecraft.BetterSleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin{

	private static Plugin instance;
	private static BetterSleep betterSleep;
	
	public static Plugin getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		betterSleep = new BetterSleep();
		Bukkit.getPluginManager().registerEvents(betterSleep, this);
	}
	@Override
	public void onDisable() {}
	
}
