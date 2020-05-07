package xyz.revature.BankManager.cli;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import xyz.revature.BankManager.models.Account;
import xyz.revature.BankManager.models.User;
import xyz.revature.BankManager.services.AccountService;
import xyz.revature.BankManager.services.UserService;

public class Menu {

	private LinkedHashMap<Integer, Action> options = new LinkedHashMap<>();
	private StringBuilder message = new StringBuilder("\nOptions:");

	static final Scanner strInput = new Scanner(System.in);
	static final Scanner intInput = new Scanner(System.in);
	
	private User visitor;
	private Account account;
	
	private UserService us;
	private AccountService as;
	
	public Menu() {
		super();
		setOptions();
	}
	
	public Menu(User visitor) {
		super();
		setOptions(visitor);
	}
	
	public void echo() {
		for (Map.Entry<Integer, Action> entry: options.entrySet()) {
			message.append("\n" + entry.getKey() + ": " + entry.getValue().description());
		}
		System.out.println("\n\nAccount Manager");
		System.out.println(message.toString());
	}
	
	void echo(StringBuilder message) {
		System.out.println(message.toString());
	}

	public void parse() {
	    System.out.print("\nSelect option: ");
	    int selected;
	    while (!(intInput.hasNextInt())) {
	    	System.out.println("\nInvalid selection.");
	    	System.out.print("\nSelect option: ");
		    intInput.next();
	    }
	    selected = intInput.nextInt();
	    if (options.containsKey(selected)) {
	    	options.get(selected).invoke();
	    } else {
	    	System.out.println("\nInvalid selection: " + selected);
	    	echo(message);
	    	parse();
	    }
	    
	}
	
	/*
	 * Options before signing in:
	 * 		1. Sign in
	 * 		2. Open account
	 * 		3. Exit
	 */
	
	void setOptions() {
		// Sign in
		options.put(1, new Action() {
			@Override
			public void invoke() {
				us = new UserService();
				System.out.print("\nEnter username: ");
				String usernameInput = strInput.nextLine();
				int id = us.userId(usernameInput);
				while (id == 0) {
					System.out.println("\nUsername not found.");
					System.out.print("\nEnter username: ");
					usernameInput = strInput.nextLine();
					id = us.userId(usernameInput);
				}
				System.out.print("\nEnter passphrase: ");
				String passphraseInput = strInput.nextLine();
				boolean authenticated = us.signIn(usernameInput, passphraseInput);
				while (!(authenticated)) {
					System.out.println("\nPassphrase incorrect.");
					System.out.print("\nEnter passphrase: ");
					passphraseInput = strInput.nextLine();
					authenticated = us.signIn(usernameInput, passphraseInput);
				}
				visitor = new User(id, usernameInput, passphraseInput);
				as = new AccountService();
				account = new Account(id);
				account.setId(id);
				visitor.setUserAccount(account);
			}
			@Override
			public String description() {
				return "Sign in to existing account.";
			}
		});
		// Open account
		options.put(2, new Action() {
			@Override
			public void invoke() {
				us = new UserService();
				System.out.print("\nEnter username: ");
				String usernameInput = strInput.nextLine();
				boolean validUn = validUsername(usernameInput);
				// Sentinel loops to validate inputs.
				while (!(validUn)) {
					System.out.println("\nUsernames require 3-60 letters or digits.");
					System.out.print("\nEnter username: ");
					usernameInput = strInput.nextLine();
					validUn = validUsername(usernameInput);
				}
				// If username is taken, try another, and check its validity.
				int uid = us.userId(usernameInput);
				while (uid != 0) {
					System.out.println("\nUsername unavailable.");
					System.out.print("\nEnter username: ");
					usernameInput = strInput.nextLine();
					uid = us.userId(usernameInput);
				}
				System.out.print("\nEnter passphrase: ");
				String passphraseInput = strInput.nextLine();
				boolean validPp = validPassphrase(passphraseInput);
				while (!(validPp)) {
					System.out.println("\nPassphrases require 8-60 letters and digits.");
					System.out.print("\nEnter passphrase: ");
					passphraseInput = strInput.nextLine();
					validPp = validPassphrase(passphraseInput);
				}
				visitor = new User(usernameInput, passphraseInput);
				visitor.setId(us.addUser(visitor));
				as = new AccountService();
				account = new Account(visitor.getId());
				account.setId(as.addAccount(account));
				visitor.setUserAccount(account);
			}
			@Override
			public String description() {
				return "Open an account.";
			}
		});
		options.put(3, new Action() {
			@Override
			public void invoke() {
				System.out.println("\nExiting program.");
				System.exit(0);
			}
			@Override
			public String description() {
				return "Exit program.";
			}
		});
	}
	
	/*
	 * Options before signing in:
	 * 		1. Balance inquiry
	 * 		2. Withdraw
	 * 		3. Deposit
	 * 		4. Sign out
	 */

	void setOptions(final User u) {
		options.put(1, new Action() {
			@Override
			public void invoke() {
				as = new AccountService();
				int balance = as.balanceInquiry(u.getUserAccount());
				System.out.println("\n\nAccount balance: " + balance);
				mainMenu(u);
			}
			@Override
			public String description() {
				return "Balance inquiry.";
			}
		});
		options.put(2, new Action() {
			@Override
			public void invoke() {
				as = new AccountService();
				System.out.println("\nAmount: ");
				int amount;
			    while (!(intInput.hasNextInt())) {
			    	System.out.println("\nInvalid selection.");
			    	System.out.print("\nAmount: ");
				    intInput.next();
			    }
			    amount = intInput.nextInt();
			    int balance = as.balanceInquiry(u.getUserAccount());
			    // First calls balanceInquiry to confirm amount does not exceed balance.
			    amount = validWithdrawal(amount, balance);
			    balance -= amount;
			    int updates = as.withdraw(u.getUserAccount(), balance);
			    if (updates == 0) {
			    	System.out.println("Withdrawal could not be made.");
			    	mainMenu(u);
			    }
			    System.out.println("Withdrawal made for " + amount + "¢.");
			    mainMenu(u);
			}
			@Override
			public String description() {
				return "Withdraw funds.";
			}
		});
		options.put(3, new Action() {
			@Override
			public void invoke() {
				as = new AccountService();
				System.out.println("\nAmount: ");
				int amount;
			    while (!(intInput.hasNextInt())) {
			    	System.out.println("\nInvalid selection.");
			    	System.out.print("\nAmount: ");
				    intInput.next();
			    }
			    amount = intInput.nextInt();
			    int balance = as.balanceInquiry(u.getUserAccount());
			    balance += amount;
			    int updates = as.deposit(u.getUserAccount(), balance);
			    if (updates == 0) {
			    	System.out.println("Deposit could not be made.");
			    	mainMenu(u);
			    }
			    System.out.println("Deposit made for " + amount + "¢.");
			    mainMenu(u);

			}
			@Override
			public String description() {
				return "Deposit funds.";
			}
		});
		options.put(4, new Action() {
			@Override
			public void invoke() {
				System.out.println("\nSigning out.");
				signOut(visitor);
				
			}
			@Override
			public String description() {
				return "Sign out.";
			}
		});
	}
	
	boolean validUsername(String s) {
		if ((s.length() >= 3) && (s.length() <= 60) && (s.matches("[a-zA-Z0-9]*"))) {
			return true;
		}
		return false;
	}
	
	boolean validPassphrase(String s) {
		if ((s.length() >= 8) && (s.length() <= 60)) {
			return true;
		}
		return false;
	}
	
	int validWithdrawal(int amount, int balance) {
	    while (amount > balance) {
	    	System.out.println("Withdrawal amount exceeds account balance.");
		    if (!(intInput.hasNextInt())) {
		    	System.out.println("\nInvalid selection.");
		    	System.out.print("\nSelect option: ");
			    intInput.next();
			    amount = intInput.nextInt();
		    }
		    amount = intInput.nextInt();
	    }
	    return amount;
	}

	public User update() {
		return visitor;
	}
	
	void signOut(User u) {
		u = null;
		options = new LinkedHashMap<>();
		message = new StringBuilder("\nOptions:");
	}

	void mainMenu(User u) {
		options = new LinkedHashMap<>();
		message = new StringBuilder("\nOptions:");
		setOptions(u);
		echo();
		parse();
	}
	
	
}

interface Action {
	void invoke();
	String description();
}




