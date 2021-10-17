package ch.hatbe2113.vibeeconomy.io;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ch.hatbe2113.vibeeconomy.main.Main;

public class CustomConfigHandler {

	private FileConfiguration config;
	private File configFile;
	
	public CustomConfigHandler(Main main, String name) {
		this.configFile = new File(main.getDataFolder(), name + ".yaml");
		this.config = YamlConfiguration.loadConfiguration(configFile);
		
		this.save();
	}
	
	public void save() {
		try {
			this.config.save(this.configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setDefaults(String path, Object obj) {
		this.config.addDefault(path, obj);
		this.config.options().copyDefaults(true);
		this.save();
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
