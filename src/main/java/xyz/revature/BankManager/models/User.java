package xyz.revature.BankManager.models;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String passphrase;

	private Account account;
	
	public User(int id, String name, String pass) {
		super();
		this.id = id;
		this.username = name;
		this.passphrase = pass;
		//account = new Account();
	}
	public User(String name, String pass) {
		super();
		this.id = id;
		this.username = name;
		this.passphrase = pass;
		//account = new Account();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return this.username;
	}
	void setName(String name) {
		this.username = name;
	}
	public String getPassphrase() {
		return passphrase;
	}
	void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}
	
	public void setUserAccount(Account a) {
		account = a;
	}
	
	public Account getUserAccount() {
		return account;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((passphrase == null) ? 0 : passphrase.hashCode());
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (passphrase == null) {
			if (other.passphrase != null)
				return false;
		} else if (!passphrase.equals(other.passphrase))
			return false;
		return true;
	}
	
}

