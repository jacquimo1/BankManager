package xyz.revature.BankManager.models;

import xyz.revature.BankManager.models.Account;

public class Account {
	private int id;
	private int holder;
	private int balance;

	public Account(int accountHolder) {
		super();
		this.holder = accountHolder;
		this.balance = 0;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHolder() {
		return holder;
	}
	public void setHolder(int holder) {
		this.holder = holder;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + balance;
		result = prime * result + holder;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (balance != other.balance)
			return false;
		if (holder != other.holder)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
