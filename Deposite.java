import java.io.IOException;

public class Deposite {
    private AccountService accountService;
    private DepositOperation depositOperation;

    public Deposite(AccountService accountService) {
        this.accountService = accountService;
        this.depositOperation = (balance, amount) -> balance + amount;
    }

    public void deposit(double amount) {
        try {
            String[] accountInfo = accountService.readAccountFromFile();
            String name = accountInfo[0];
            double balance = Double.parseDouble(accountInfo[1]);

            double newBalance = depositOperation.deposit(balance, amount);
            System.out.println(name + " deposited: " + amount);
            System.out.println("New Balance: " + newBalance);

            accountService.writeAccountToFile(name, newBalance, accountInfo[2]);
        } catch (IOException e) {
            System.out.println("Error reading or writing file: " + e.getMessage());
        }
    }
}
