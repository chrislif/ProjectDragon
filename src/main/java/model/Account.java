package model;

public class Account {
    private int accountID;
    private String accountName;
    private String email;

    public Account() {
    }

    public Account(String accountName, String email) {
        this.accountName = accountName;
        this.email = email;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
