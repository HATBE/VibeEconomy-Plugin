package ch.hatbe2113.vibeeconomy.account;

import ch.hatbe2113.vibeeconomy.io.CustomConfigHandler;

public class Account {
	
	private CustomConfigHandler account;
	private String uuid;
	private int balance;

	public Account(CustomConfigHandler account, String uuid) {
		this.account = account;
		this.uuid = uuid;
		
		if(account.getString(uuid.toString()) == null) {
			return;
		}
		
		this.balance = this.account.getInt(this.uuid + ".balance");
		
	}
	
	public boolean exists() {
		return this.account.getString(this.uuid) == null ? false : true;
	}
	
	public void addMoney(int amount) {
		this.balance += amount;
		this.account.set(this.uuid + ".balance", this.balance);
		this.account.save();
	}
	
	public void removeMoney(int amount) {
		this.balance -= amount;
		this.account.set(this.uuid + ".balance", this.balance);
		this.account.save();
	}
	
	public void setMoney(int amount) {
		this.balance = amount;
		this.account.set(this.uuid + ".balance", this.balance);
		this.account.save();
	}
	
	public int getBalance() {
		return this.balance;
	}
	
}
