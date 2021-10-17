package ch.hatbe2113.vibeeconomy.io;

import org.bukkit.configuration.file.FileConfiguration;

import ch.hatbe2113.vibeeconomy.main.Main;

public class ConfigHandler {

	private Main main;
	private FileConfiguration config;
	
	public ConfigHandler(Main main) {
		this.main = main;
		this.config = main.getConfig();
		
		this.save();
	}
	
	public FileConfiguration getConfig() {
		return this.config;
	}
	
	public void save() {
		main.saveConfig();
	}
	
	public void setDefaults(String path, Object obj) {
		this.config.addDefault(path, obj);
		this.config.options().copyDefaults(true);
		this.main.saveConfig();
	}
	
	public void set(String path, Object obj) {
		this.config.set(path, obj);
	}
	
	public void delete(String path) {
		this.config.set(path,  null);
	}
	
	public String getString(String path) {
		String str = this.config.getString(path);
		return str;
	}
	
	public Double getDouble(String path) {
		Double d = this.config.getDouble(path);
		return d;
	}
	
	public int getInt(String path) {
		int i = this.config.getInt(path);
		return i;
	}
	
}

