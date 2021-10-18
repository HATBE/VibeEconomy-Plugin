package ch.hatbe2113.vibeeconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.TextOutput;

public class RemoveCommand {
	private CustomConfigHandler lang, account, playerLookupTable;
	private CommandSender sender;
	private String[] args;
	
	public RemoveCommand(CustomConfigHandler lang, CustomConfigHandler account, CustomConfigHandler playerLookupTable, CommandSender sender, String[] args) {
		this.lang = lang;
		this.account = account;
		this.playerLookupTable = playerLookupTable;
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
		
		if(targetAccount.getBalance() < amount) {
			amount = targetAccount.getBalance();
			System.out.println("true");
		}
		
		targetAccount.removeMoney(amount);

		if(t != null) {
			TextOutput.outputToPlayer(t, this.lang.getString("messages.targetRemovedMoney")
					.replace("{SENDER}", p.getDisplayName())
					.replace("{AMOUNT}", amount + "")
					.replace("{COIN}", this.lang.getString("names.coin")));
		}
		TextOutput.outputToPlayer(p, this.lang.getString("messages.senderRemovedMoney")
				.replace("{TARGET}", args[1])
				.replace("{AMOUNT}", amount + "")
				.replace("{COIN}", this.lang.getString("names.coin")));
	}
}
