package xyz.revature.BankManager.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import xyz.revature.BankManager.models.User;
import xyz.revature.BankManager.util.DatabaseConnecter;

public class UserDaoImpl implements UserDao {
	
	public int addUser(User u){
		int id = 0;
		String sql = "{call add_user(?, ?)}";
		ResultSet rs = null;
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				CallableStatement cs = c.prepareCall(sql)){
			cs.setString(1, u.getName());
			cs.setString(2, u.getPassphrase());
			cs.execute();
			rs = cs.getResultSet();
			while (rs.next()) {
				id = rs.getInt(1);
				u.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}

	public int userId(String name) {
		int id = 0;
		String sql = "select user_id from person where user_name = ?";
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public boolean signIn(String username, String password) {
		boolean authenticated = false;
		String sql = "select * from person where user_name = ? and passphrase = ?";
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				PreparedStatement ps = c.prepareStatement(sql);){
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			authenticated = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return authenticated;
	}

}
