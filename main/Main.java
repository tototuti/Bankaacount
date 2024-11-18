package main;
import models.User;
//import services.DepositService;
import services.WithdrawService;
import utilities.Write;


import utilities.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Write writer = new Write();
        UserService.DepositService depositService = new UserService.DepositService(writer);
        WithdrawService withdrawService = new WithdrawService(writer);

        System.out.println("Welcome to the Bank System!");


        System.out.print("Enter your models.User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String enteredPassword = scanner.nextLine();

        try {
            List<User> users = UserService.readUsersFromFile();
            User currentUser = users.stream()
                    .filter(user -> user.getUserId().equals(userId) && user.getPassword().equals(enteredPassword))
                    .findFirst()
                    .orElse(null);

            if (currentUser == null) {
                System.out.println("Invalid models.User ID or Password. Access Denied.");
                return;
            }

            System.out.println("Login successful!");
            System.out.println("Welcome, " + currentUser.getUsername() + "!");

            // Menu loop for user operations
            while (true) {
                System.out.println("\nBank Account Menu:");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. View All Accounts Information");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice;
                try {
                    choice = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    scanner.nextLine(); // Clear the scanner buffer
                    continue;
                }

                switch (choice) {
                    case 1:
                        // Check balance
                        System.out.println("Current Balance: " + currentUser.getBalance());
                        break;

                    case 2:
                        // Deposit money
                        System.out.print("Enter the amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        depositService.deposit(userId, depositAmount);
                        break;

                    case 3:
                        // Withdraw money
                        System.out.print("Enter the amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        withdrawService.withdraw(userId, withdrawAmount);
                        break;

                    case 4:
                        // View all account details
                        System.out.println("All models.User Information:");
                        List<User> allUsers = UserService.readUsersFromFile();
                        allUsers.forEach(user -> System.out.println(
                                "ID: " + user.getUserId() +
                                        " | Name: " + user.getUsername() +
                                        " | Balance: " + user.getBalance()
                        ));
                        break;

                    case 5:
                        // Exit
                        System.out.println("Thank you yousef for using my Bank System. Goodbye!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error accessing user data: " + e.getMessage());
        }
    }
}
