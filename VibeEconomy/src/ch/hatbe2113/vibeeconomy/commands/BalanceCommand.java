package ch.hatbe2113.vibeeconomy.commands;

import java.text.DecimalFormat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.TextOutput;

public class BalanceCommand implements CommandExecutor {
	
	private CustomConfigHandler lang, account;
	private DecimalFormat formatter = new DecimalFormat("#,###");
	
	public BalanceCommand(CustomConfigHandler lang, CustomConfigHandler account) {
		this.lang = lang;
		this.account = account;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			TextOutput.outputToCommandSender(sender, this.lang.getString("errors.onlyPlayers"));
			return false;
		}
		
		Player p = (Player) sender;
		
		if(args.length == 0) {
			String playerUuid = p.getUniqueId().toString();
			Account account = new Account(this.account, playerUuid);

			TextOutput.outputToPlayer(p, this.lang.getString("messages.balance")
					.replace("{BALANCE}", "" + formatter.format(account.getBalance()))
					.replace("{COIN}", this.lang.getString("names.coin")));
		} else {
			TextOutput.outputToPlayer(p, this.lang.getString("errors.wrongFormat")
					.replace("{USAGE}", "/" + label + " [user]"));
		}
		
		return false;
	}
}
