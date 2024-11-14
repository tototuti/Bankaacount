import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        Deposite deposite = new Deposite(accountService);
        Withdraw withdraw = new Withdraw(accountService);

        Scanner scanner = new Scanner(System.in);

        try {
            accountService.writeAccountToFile("John Doe", 1000.0, "password123");
        } catch (IOException e) {
            System.out.println("Error writing initial account info: " + e.getMessage());
            return;
        }

        System.out.print("Enter your password: ");
        String enteredPassword = scanner.nextLine();

        if (accountService.confirmPassword(enteredPassword)) {
            while (true) {
                System.out.println("\nBank Account Menu:");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        try {
                            String[] accountInfo = accountService.readAccountFromFile();
                            System.out.println(accountInfo[0] + "'s Current Balance: " + accountInfo[1]);
                        } catch (IOException e) {
                            System.out.println("Error reading file: " + e.getMessage());
                        }
                        break;

                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        deposite.deposit(depositAmount);
                        break;

                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        withdraw.performWithdraw(withdrawAmount);
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } else {
            System.out.println("Incorrect password.");
        }
    }
}
