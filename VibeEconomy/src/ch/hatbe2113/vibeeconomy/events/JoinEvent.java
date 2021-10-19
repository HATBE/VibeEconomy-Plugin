package ch.hatbe2113.vibeeconomy.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.ConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomLogger;
import ch.hatbe2113.vibeeconomy.io.TextOutput;

public class JoinEvent implements Listener {
	
	private CustomConfigHandler playerLookupTable, account, lang;
	private CustomLogger logger;
	private ConfigHandler config;
	
	public JoinEvent(CustomConfigHandler lang, CustomConfigHandler playerLookupTable, CustomConfigHandler account, CustomLogger logger, ConfigHandler config) {
		this.playerLookupTable = playerLookupTable;
		this.account = account;
		this.logger = logger;
		this.config = config;
		this.lang = lang;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(playerLookupTable.getString(e.getPlayer().getDisplayName()) == null) {
			playerLookupTable.set(e.getPlayer().getName().toLowerCase(), e.getPlayer().getUniqueId().toString());
			playerLookupTable.save();
			TextOutput.outputToConsole("Added Player " + e.getPlayer().getName().toString() + " to Player Lookup Table");
		}
		
		Account account = new Account(this.account, e.getPlayer().getUniqueId().toString());
		
		if(!account.exists()) {
			account.setMoney(this.config.getInt("starter.money"));
			logger.info("[Starter] Added " + e.getPlayer().getName() + " " + this.config.getInt("starter.money") + " " + this.lang.getString("names.coin"));
		}
	}
	
}
