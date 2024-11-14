public class Account {


    private String accountNumber;
    private double balance;


    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }


    public String getAccountNumber() {
        return accountNumber;
    }


    public double getBalance() {
        return balance;
    }


    protected void setBalance(double balance) {
        this.balance = balance;
    }
}



