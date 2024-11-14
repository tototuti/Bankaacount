import java.io.IOException;

public class Withdraw {
    private AccountService accountService;
    private WithdrawOperation withdrawOperation;

    public Withdraw(AccountService accountService) {
        this.accountService = accountService;
        this.withdrawOperation = (balance, amount) -> balance >= amount ? balance - amount : balance; // Lambda for withdraw
    }

    public void performWithdraw(double amount) {
        try {
            String[] accountInfo = accountService.readAccountFromFile();
            String name = accountInfo[0];
            double balance = Double.parseDouble(accountInfo[1]);

            // Perform withdrawal using the lambda
            double newBalance = withdrawOperation.withdraw(balance, amount);

            if (newBalance < balance) {
                System.out.println(name + " withdrew: " + amount);
                System.out.println("New Balance: " + newBalance);
                accountService.writeAccountToFile(name, newBalance, accountInfo[2]);
            } else {
                System.out.println("Invalid or insufficient funds for withdrawal!");
            }
        } catch (IOException e) {
            System.out.println("Error reading or writing file: " + e.getMessage());
        }
    }
}
