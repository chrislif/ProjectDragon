/*

 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDB {
    public static Boolean createAccount (String username) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        
        if (connection == null) {
            return false;
        }
        
        PreparedStatement statement = null;
        
        String query 
                = "INSERT INTO account (accountName) "
                + "VALUES (?)";
        
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                pool.freeConnection(connection);
            } catch (SQLException ex) {
                throw ex;
            }
        }
        return true;
    }
}
