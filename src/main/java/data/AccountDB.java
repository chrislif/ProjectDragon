package data;

import model.Account;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDB {
    public static Account getAccount (int accountID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account user = null;

        String query = "SELECT * FROM account WHERE accountID = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, accountID);
            
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                user = new Account(
                                resultSet.getString("accountName"), 
                                resultSet.getString("email"));
            }
            return user;
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } 
            catch (SQLException e) {
                throw e;
            }
        }
    }

    public static ArrayList<Account> getAllAccounts(Account admin) throws SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<Account> accountList = new ArrayList();

        String query = "SELECT * FROM account";
        
        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            
            Account user;
            while (resultSet.next()) {
                user = new Account(
                                resultSet.getString("accountName"), 
                                resultSet.getString("email"));
                accountList.add(user);
            }
            return accountList;
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (resultSet != null && statement != null) {
                    resultSet.close();
                    statement.close();
                }
                pool.freeConnection(connection);
            } 
            catch (SQLException e) {
                throw e;
            }
        }
    }
}