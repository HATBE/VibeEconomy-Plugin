package ch.hatbe2113.vibeeconomy.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.ConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomLogger;
import ch.hatbe2113.vibeeconomy.io.TextOutput;
import ch.hatbe2113.vibeeconomy.main.Main;

public class JoinEvent implements Listener {
	
	private CustomConfigHandler playerLookupTable, account, lang;
	private CustomLogger logger;
	private ConfigHandler config;
	
	public JoinEvent(Main main) {
		this.playerLookupTable = main.getPlayerLookupTable();
		this.account = main.getAccount();
		this.logger = main.getMoneyLogger();
		this.config = main.getConfiguration();
		this.lang = main.getLang();
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
