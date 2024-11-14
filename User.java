
public class User {
    private int number;
    private String name;
    private String password;
    private double balance;

    public User(int number, String name, String password, double balance) {
        this.number = number;
        this.name = name;
        this.password = password;
        this.balance = balance;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
