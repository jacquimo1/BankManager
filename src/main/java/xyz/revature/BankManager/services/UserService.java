package xyz.revature.BankManager.services;

import java.sql.Connection;
import java.sql.SQLException;

import xyz.revature.BankManager.daos.UserDao;
import xyz.revature.BankManager.daos.UserDaoImpl;
import xyz.revature.BankManager.models.User;

public class UserService {
	private UserDao ud = new UserDaoImpl();
	
	public int userId(String s) {
		return ud.userId(s);
	}
	
	public int addUser(User u) {
		return ud.addUser(u);
	}
	
	public boolean signIn(String un, String pp) {
		return ud.signIn(un, pp);
	}
}
