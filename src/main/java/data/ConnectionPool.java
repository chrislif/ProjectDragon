package data;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class.getName());

    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            //This links up with the resource tag in context.xml
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/data");
        } catch (NamingException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            Connection conn = dataSource.getConnection();
            return conn;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
            System.out.print(e.getMessage());
            return null;
        }
    }
    
    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }
}
