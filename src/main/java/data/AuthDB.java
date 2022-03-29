package data;

import model.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDB {
    public static Boolean createAccount (Account account, String salt, String password) throws SQLException, NoSuchAlgorithmException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query 
                = "INSERT INTO account (email, accountName, salt, hash) "
                + "VALUES (?, ?, ?, ?)";
        
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, account.getEmail());
        statement.setString(2, account.getAccountName());
        statement.setString(3, salt);

        try {
            String hash = hashPassword(password, salt);
            statement.setString(4, hash);
        } 
        catch (NoSuchAlgorithmException ex) {
            throw ex;
        }

        try {
            statement.executeUpdate();
        } 
        catch (SQLException ex) {
            throw ex;
        } 
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } 
            catch (SQLException ex) {
                throw ex;
            }
            pool.freeConnection(connection);
        }  
        return true;
    }

    public static Account loginAccount (String email, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account user = null;

        String query = "SELECT * FROM account WHERE email = ?";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            
            resultSet = statement.executeQuery();
            resultSet.next();

            if (compareHash(password, resultSet.getString("salt"), resultSet.getString("hash"))) {
                user = new Account();
                user.setAccountID(resultSet.getInt("accountID"));
                user.setAccountName(resultSet.getString("accountName"));
                user.setEmail(resultSet.getString("email"));
            }
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
        return user;
    }

    private static Boolean compareHash(String passwordInput, String salt, String hashStored){
        try {
            String hashInput = hashPassword(passwordInput, salt);
            return hashInput.equals(hashStored);
        } 
        catch (NoSuchAlgorithmException ex) {
            return false;
        }
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            md.update(salt.getBytes());
            md.update(password.getBytes());
            
            byte[] byteHash = md.digest();
            return bytesToHex(byteHash);
        } 
        catch (NoSuchAlgorithmException ex) {
            throw ex;
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
