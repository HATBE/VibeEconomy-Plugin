package ch.hatbe2113.vibeeconomy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ch.hatbe2113.vibeeconomy.account.Account;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomLogger;
import ch.hatbe2113.vibeeconomy.io.TextOutput;
import ch.hatbe2113.vibeeconomy.main.Main;

public class PayCommand implements CommandExecutor {
	
	private CustomConfigHandler lang, account, playerLookupTable;
	private CustomLogger logger;
	
	public PayCommand(Main main) {
		this.lang = main.getLang();
		this.account = main.getAccount();
		this.playerLookupTable = main.getPlayerLookupTable();
		this.logger = main.getMoneyLogger();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			TextOutput.outputToCommandSender(sender, this.lang.getString("errors.onlyPlayers"));
			return false;
		}
		
		Player p = (Player) sender;
		
		if(args.length == 2) {
			int amount = 0;
			
			Account account = new Account(this.account, p.getUniqueId().toString());
			
			if(args[0].equalsIgnoreCase(p.getName())) {
				TextOutput.outputToPlayer(p, this.lang.getString("errors.notSelfAction"));
				return false;
			}
			
			String targetUuid = playerLookupTable.getString(args[0].toLowerCase());
			Player t = Bukkit.getPlayer(args[0]);
			
			try {
				amount = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				TextOutput.outputToPlayer(p, this.lang.getString("errors.notNumeric"));
				return false;
			}
			
			if(targetUuid == null) {
				TextOutput.outputToPlayer(p, this.lang.getString("errors.playerNotExist"));
				return false;
			}
			
			Account targetAccount = new Account(this.account, targetUuid);
			
			if(account.getBalance() < amount) {
				TextOutput.outputToPlayer(p, this.lang.getString("errors.notEnoughMoney"));
				return false;
			}
			
			account.removeMoney(amount);
			targetAccount.addMoney(amount);
			
			if(t != null) {
				TextOutput.outputToPlayer(t, this.lang.getString("messages.targetSentMoney")
						.replace("{SENDER}", p.getDisplayName())
						.replace("{AMOUNT}", amount + "")
						.replace("{COIN}", this.lang.getString("names.coin")));
			}
			TextOutput.outputToPlayer(p, this.lang.getString("messages.senderSentMoney")
					.replace("{TARGET}", args[0])
					.replace("{AMOUNT}", amount + "")
					.replace("{COIN}", this.lang.getString("names.coin")));
			this.logger.info("Player " + p.getName() + " transfered " + amount + " " + this.lang.getString("names.coin") + " to " + args[0]);
			
		} else {
			TextOutput.outputToPlayer(p, this.lang.getString("errors.wrongFormat")
					.replace("{USAGE}", "/" + label + " <user> <amount>"));
		}
		
		return false;
	}
	
}
