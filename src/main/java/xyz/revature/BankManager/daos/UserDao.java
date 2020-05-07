package xyz.revature.BankManager.daos;

import java.util.List;

import xyz.revature.BankManager.models.User;

public interface UserDao {
	public int userId(String s);
	public int addUser(User u);
	public boolean signIn(String un, String pp);
}
