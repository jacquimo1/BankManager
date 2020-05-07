package xyz.revature.BankManager.services;


import xyz.revature.BankManager.daos.AccountDao;
import xyz.revature.BankManager.daos.AccountDaoImpl;
import xyz.revature.BankManager.models.Account;

public class AccountService {
	private AccountDao ad = new AccountDaoImpl();
	
	public int addAccount(Account account) {
		return ad.addAccount(account);
	}
	
	public int balanceInquiry(Account account) {
		return ad.balanceInquiry(account);
	}
	
	public int withdraw(Account account, int amount) {
		return ad.withdraw(account, amount);
	}
	
	public int deposit(Account account, int amount) {
		return ad.deposit(account, amount);
	}
}
