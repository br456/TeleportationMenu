package me.br456.Telepads;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Telepads extends JavaPlugin{
	
	private static Map<String, Pad> telepads = new HashMap<String, Pad>();
	SettingsManager settings = SettingsManager.getInstance();
	private static Plugin plugin;
	
	public void onEnable() {
		settings.setup(this);
		settings.saveConfig();
		plugin = this;
		
		registerCommands();
		registerEvents();
		
		loadTelepads();
	}
	
	public void onDisable() {
		saveTelepads();
	}
	
	private void registerCommands() {
		getCommand("telepad").setExecutor(new TelepadCommand());
	}
	
	private void registerEvents() {
		getServer().getPluginManager().registerEvents(new TelepadEvent(), this);
	}
	
	private void saveTelepads() {
		if(!getPlugin().getDataFolder().exists()) {
			getPlugin().getDataFolder().mkdir();
		}
		try {
			SLAPI.save(telepads, getPlugin().getDataFolder() + File.separator + "telepads.bin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadTelepads() {
		String path = getPlugin().getDataFolder() + File.separator + "telepads.bin";
		File file = new File(path);
		
		if(file.exists()) {
			try {
				telepads = SLAPI.load(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static Map<String, Pad> getTelepads() {
		return telepads;
	}

}
