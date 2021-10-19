package ch.hatbe2113.vibeeconomy.commands;

import java.text.DecimalFormat;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.TextOutput;
import ch.hatbe2113.vibeeconomy.main.Main;

public class BankBalanceCommand {
	private CustomConfigHandler lang, account, playerLookupTable;
	private CommandSender sender;
	private String[] args;
	private DecimalFormat formatter = new DecimalFormat("#,###");
	
	public BankBalanceCommand(Main main, CommandSender sender, String[] args) {
		this.lang = main.getLang();
		this.account = main.getAccount();
		this.playerLookupTable = main.getPlayerLookupTable();
		this.sender = sender;
		this.args = args;
		
		this.start();
	}
	
	private void start() {
		Player p  = (Player) this.sender;
		
		String targetUuid = playerLookupTable.getString(args[1].toLowerCase());
		
		if(targetUuid == null) {
			TextOutput.outputToPlayer(p, this.lang.getString("errors.playerNotExist"));
			return;
		}
		
		Account targetAccount = new Account(this.account, targetUuid);
	
		TextOutput.outputToPlayer(p, this.lang.getString("messages.targetBalance")
				.replace("{TARGET}", args[1])
				.replace("{BALANCE}", "" + formatter.format(targetAccount.getBalance()))
				.replace("{COIN}", this.lang.getString("names.coin")));
	}


}
