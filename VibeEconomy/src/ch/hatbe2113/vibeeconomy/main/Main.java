package ch.hatbe2113.vibeeconomy.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import ch.hatbe2113.vibeeconomy.commands.BalanceCommand;
import ch.hatbe2113.vibeeconomy.commands.BankCommand;
import ch.hatbe2113.vibeeconomy.commands.PayCommand;
import ch.hatbe2113.vibeeconomy.events.JoinEvent;
import ch.hatbe2113.vibeeconomy.io.ConfigHandler;
import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;
import ch.hatbe2113.vibeeconomy.io.TextOutput;

public class Main extends JavaPlugin {
	
	// 17.10.2021 HATBE2113
	
	private ConfigHandler config;
	private CustomConfigHandler account, lang, playerLookupTable;

	public void onEnable() {
		this.enableMessage();
		
		this.registerConfigs();
		this.registerComamnds();
		this.registerEvents();
	}
	
	public void onDisable() {
		this.disableMessage();
	}
	
	private void registerComamnds() {
		this.getCommand("pay").setExecutor(new PayCommand(this.lang, this.account, this.playerLookupTable));
		this.getCommand("balance").setExecutor(new BalanceCommand(this.lang, this.account));
		this.getCommand("bank").setExecutor(new BankCommand(this.lang, this.account, this.playerLookupTable));
	}
	
	private void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new JoinEvent(this.playerLookupTable), this);
	}
	
	private void registerConfigs() {
		this.config = new ConfigHandler(this);
		this.config.setDefaults("account.maxCount", 3);
		this.config.save();
		
		this.lang = new CustomConfigHandler(this, "lang");
		
		this.lang.setDefaults("names.coin", "VibeCoins");
		this.lang.setDefaults("errors.onlyPlayers", "You have to be a Player to execute this command!");
		this.lang.setDefaults("errors.wrongFormat", "Wrong format, use {USAGE}!");
		this.lang.setDefaults("errors.noAccount", "The Account {ACCOUNT} does not exist!");
		this.lang.setDefaults("errors.playerNotExist", "This player does not exist!");
		this.lang.setDefaults("errors.notSelfAction", "Can't perform this action on yourself!");
		this.lang.setDefaults("errors.notNumeric", "Please insert a number as value!");
		this.lang.setDefaults("errors.notEnoughMoney", "You don't have enough money to afford this!");
		this.lang.setDefaults("errors.noPerms", "You don't have the permission to execute this command!");
		this.lang.setDefaults("messages.balance", "You're current balance: {BALANCE} {COIN}.");
		this.lang.setDefaults("messages.targetSentMoney", "{SENDER} sent you {AMOUNT} {COIN}.");
		this.lang.setDefaults("messages.senderSentMoney", "You sent {TARGET} {AMOUNT} {COIN}.");
		this.lang.setDefaults("messages.targetSetMoney", "{SENDER} set you're account to {AMOUNT} {COIN}.");
		this.lang.setDefaults("messages.senderSetMoney", "You set {TARGET}'s account to {AMOUNT} {COIN}.");
		this.lang.save();
		
		this.account = new CustomConfigHandler(this, "account");
		
		this.playerLookupTable = new CustomConfigHandler(this, "playerLookupTable");
	}
	
	private void disableMessage() {
		TextOutput.outputToConsole("");
		TextOutput.outputToConsole("---------------------------------");
		TextOutput.outputToConsole("");
		TextOutput.outputToConsole("Shutdown", true);
		TextOutput.outputToConsole("");
		TextOutput.outputToConsole("---------------------------------");
		TextOutput.outputToConsole("");
	}
	
	private void enableMessage() {
		TextOutput.outputToConsole("");
		TextOutput.outputToConsole("---------------------------------");
		TextOutput.outputToConsole("");
		TextOutput.outputToConsole("Startup", true);
		TextOutput.outputToConsole("");
		TextOutput.outputToConsole("---------------------------------");
		TextOutput.outputToConsole("");
	}
	
}
