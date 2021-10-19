package ch.hatbe2113.vibeeconomy.io;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import ch.hatbe2113.vibeeconomy.main.Main;

public class CustomLogger {

	private Logger logger;
	private FileHandler fh;
	
	public CustomLogger(Main main, String name) {
		this.logger = Logger.getLogger(name);
		
		String dirName = main.getDataFolder() + "/" + name + "Logs/";
		
		try {
		    File directory = new File(dirName);
		    if(! directory.exists()) {
		    	directory.mkdir();
		    }
			
			this.fh = new FileHandler(dirName + name + ".log");
		    this.logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
	        this.fh.setFormatter(formatter);  
		} catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	public void info(String msg) {
		this.logger.info(msg);
	}
	
	public void warning(String msg) {
		this.logger.warning(msg);
	}
	
}
