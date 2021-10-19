package ch.hatbe2113.vibeeconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomLogger;
import ch.hatbe2113.vibeeconomy.io.TextOutput;
import ch.hatbe2113.vibeeconomy.main.Main;

public class BankAddCommand {
	private CustomConfigHandler lang, account, playerLookupTable;
	private CustomLogger logger;
	private CommandSender sender;
	private String[] args;
	
	public BankAddCommand(Main main, CommandSender sender, String[] args) {
		this.lang = main.getLang();
		this.account = main.getAccount();
		this.playerLookupTable = main.getPlayerLookupTable();
		this.logger = main.getMoneyLogger();
		this.sender = sender;
		this.args = args;
		
		this.start();
	}

	private void start() {
		Player p  = (Player) this.sender;
		
		String targetUuid = playerLookupTable.getString(args[1].toLowerCase());
		Player t = Bukkit.getPlayer(args[1]);
		int amount = 0;
		
		try {
			amount = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			TextOutput.outputToPlayer(p, this.lang.getString("errors.notNumeric"));
			return;
		}
		
		if(targetUuid == null) {
			TextOutput.outputToPlayer(p, this.lang.getString("errors.playerNotExist"));
			return;
		}
		
		Account targetAccount = new Account(this.account, targetUuid);
		
		targetAccount.addMoney(amount);

		if(t != null) {
			TextOutput.outputToPlayer(t, this.lang.getString("messages.targetSentMoney")
					.replace("{SENDER}", p.getDisplayName())
					.replace("{AMOUNT}", amount + "")
					.replace("{COIN}", this.lang.getString("names.coin")));
		}
		TextOutput.outputToPlayer(p, this.lang.getString("messages.senderSentMoney")
				.replace("{TARGET}", args[1])
				.replace("{AMOUNT}", amount + "")
				.replace("{COIN}", this.lang.getString("names.coin")));
		
		this.logger.info("Player " + p.getName() + " Added " + amount + " " + this.lang.getString("names.coin") + " to " + args[1] + "'s Account");

	}
}
