package xyz.revature.BankManager.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import xyz.revature.BankManager.models.Account;
import xyz.revature.BankManager.util.DatabaseConnecter;

public class AccountDaoImpl implements AccountDao {
	
	public int addAccount(Account a){
		int id = 0;
		String sql = "{select add_account(?, ?)}";
		ResultSet rs = null;
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				CallableStatement cs = c.prepareCall(sql)){
			cs.setInt(1, a.getHolder());
			cs.setInt(2, 0);
			cs.execute();
			rs = cs.getResultSet();
			while (rs.next()) {
				id = rs.getInt(1);
				a.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				
				e.printStackTrace();
				}
			}
		}
		return id;
	}

	public int balanceInquiry(Account account) {
		int balance = 0;
		String sql = "select account_balance from account where account_id = ?";
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, account.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				balance = rs.getInt(1);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balance;
	}
	
	public int withdraw(Account a, int amount) {
		int updated = 0;
		String sql = "update account set account_balance = ? where account_id = ?";
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, amount);
			ps.setInt(2, a.getId());
			updated = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
	
	public int deposit(Account a, int amount) {
		int updated = 0;
		String sql = "update account set account_balance = ? where account_id = ?";
		try (Connection c = DatabaseConnecter.hardcodedConnection();
				PreparedStatement ps = c.prepareStatement(sql);){
			ps.setInt(1, amount);
			ps.setInt(2, a.getId());
			updated = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updated;
	}
	
}
