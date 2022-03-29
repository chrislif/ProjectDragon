package data;

import model.Account;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDB {
    public static Boolean createAccount (Account account, String salt, String password) throws SQLException {
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
        } catch (NoSuchAlgorithmException ex) {
            return false;
        }

        executeQuery(statement);

        pool.freeConnection(connection);

        return true;
    }

    public static Account loginAccount (String email, String password) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        String query 
                = "SELECT * FROM account"
                + "WHERE email = ?";
        
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);

        ResultSet resultSet = executeResultQuery(statement);
        resultSet.next();

        Account user = null;

        

        if (compareHash(password, resultSet.getString("salt"), resultSet.getString("hash"))) {
            user = new Account(
                                resultSet.getString("accountName"), 
                                resultSet.getString("email"));

            user.setAccountID(resultSet.getInt("accountID"));
        }

        resultSet.close();
        pool.freeConnection(connection);

        return user;
    }

    public static Boolean executeQuery(PreparedStatement statement) throws SQLException {
        try {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                throw ex;
            }
        }
        return true;
    }

    public static ResultSet executeResultQuery(PreparedStatement statement) throws SQLException {
        try {
            return statement.executeQuery();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    private static Boolean compareHash(String passwordInput, String salt, String hashStored){
        try {
            String hashInput = hashPassword(passwordInput, salt);
            return hashInput.equals(hashStored);
        } catch (NoSuchAlgorithmException ex) {
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
        } catch (NoSuchAlgorithmException ex) {
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
