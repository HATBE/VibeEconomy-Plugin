package ch.hatbe2113.vibeeconomy.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.TextOutput;

public class JoinEvent implements Listener {
	
	private CustomConfigHandler playerLookupTable;
	
	public JoinEvent(CustomConfigHandler playerLookupTable) {
		this.playerLookupTable = playerLookupTable;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(playerLookupTable.getString(e.getPlayer().getDisplayName()) == null) {
			playerLookupTable.set(e.getPlayer().getName().toLowerCase(), e.getPlayer().getUniqueId().toString());
			playerLookupTable.save();
			TextOutput.outputToConsole("Added Player " + e.getPlayer().getName().toString() + " to Player Lookup Table");
		}
	}
	
}
