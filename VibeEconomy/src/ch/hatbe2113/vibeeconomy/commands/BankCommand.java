package ch.hatbe2113.vibeeconomy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.TextOutput;

public class BankCommand implements CommandExecutor {
	
	private CustomConfigHandler lang, account, playerLookupTable;
	
	public BankCommand(CustomConfigHandler lang, CustomConfigHandler account, CustomConfigHandler playerLookupTable) {
		this.lang = lang;
		this.account = account;
		this.playerLookupTable = playerLookupTable;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// Allow Only Player with permission, console and commandblock
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(!p.hasPermission("vibecoin.admin")) {
				TextOutput.outputToPlayer(p, this.lang.getString("errors.noPerms"));		
				return false;
			}
		}
		
		if(args.length == 3) {
			if(args[0].equalsIgnoreCase("set")) {
				new SetCommand(this.lang, this.account, this.playerLookupTable, sender, args);
				return false;
			} else if(args[0].equalsIgnoreCase("add")) {
				new AddCommand(this.lang, this.account, this.playerLookupTable, sender, args);
				return false;
			} else if(args[0].equalsIgnoreCase("remove")) {
				
			}
		}
		
		TextOutput.outputToCommandSender(sender, this.lang.getString("errors.wrongFormat")
				.replace("{USAGE}", "/" + label + " [set/add/remove] <user> <amount>"));		
		return false;
	}
}
