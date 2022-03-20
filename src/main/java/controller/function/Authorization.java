/*

 */
package controller.function;

import data.AuthDB;
import java.sql.SQLException;

/**
 *
 * @author chris
 */
public class Authorization {
    
    public static String testDBConnection() {
        try {
            Boolean flag = AuthDB.createAccount("Chris");
            if (flag) {
                return "Test Data Added Successfully";
            }
            else {
                return "Connection Failure";
            }
        }
        catch (SQLException ex) {
            return ex.getMessage();
        }
    }
    
}
