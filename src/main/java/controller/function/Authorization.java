
package controller.function;

import data.AuthDB;
import model.Account;
import java.sql.SQLException;
import java.util.Random;

public class Authorization {
    
    public static String createAccount(String email, String accountName, String password) {
        try {
            AuthDB.createAccount(new Account(accountName, email), randomSalt(), password);
            return "Account Created";
        } catch (SQLException ex) {
            return ex.getMessage();
        }
    }

    public static Account loginUser(String email, String password) {
        try {
            return AuthDB.loginAccount(email, password);
        } catch (SQLException ex) {
            return null;
        }
    }

    public static String randomSalt(){
        String alphanumericList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * alphanumericList.length());
            salt.append(alphanumericList.charAt(index));
        }
        return salt.toString();
    }
}
