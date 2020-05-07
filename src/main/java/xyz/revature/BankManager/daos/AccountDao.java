package xyz.revature.BankManager.daos;

import java.util.List;

import xyz.revature.BankManager.models.Account;

public interface AccountDao {
	public int addAccount(Account a);
	public int balanceInquiry(Account a);
	public int withdraw(Account a, int amount);
	public int deposit(Account a, int amount);
}
