package controller.function;

import data.AuthDB;
import model.Account;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Authorization {
    public static Boolean createAccount(String email, String accountName, String password, ArrayList<String> errorList) {
        try {
            AuthDB.createAccount(new Account(accountName, email), randomSalt(), password);
            return true;
        } 
        catch (SQLException ex) {
            errorList.add(ex.getMessage());
            return false;
        } 
        catch (NoSuchAlgorithmException ex) {
        	errorList.add(ex.getMessage());
            return false;
        }
    }

    public static Account loginUser(String email, String password, ArrayList<String> errorList) {
        try {
            return AuthDB.loginAccount(email, password);
        } 
        catch (SQLException ex) {
        	errorList.add(ex.getMessage());
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
