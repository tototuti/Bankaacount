package services;
import utilities.Write;
import models.User;


import interfaces.WithdrawalOperations;
import utilities.UserService;

import java.io.IOException;
import java.util.List;

public class WithdrawService {
    private Write writer;

    public WithdrawService(Write writer) {
        this.writer = writer;
    }

    public void withdraw(String userId, double amount) {
        try {
            if (amount <= 0) {
                System.out.println("Invalid withdrawal amount. It must be greater than zero.");
                return;
            }


            User currentUser = getUserById(userId);
            if (currentUser == null) {
                System.out.println("models.User not found. Withdrawal operation failed.");
                return;
            }


            if (currentUser.getBalance() < amount) {
                System.out.println("Insufficient funds for this withdrawal.");
                return;
            }

            double newBalance = currentUser.getBalance() - amount;
            currentUser.setBalance(newBalance);

            WithdrawalOperations withdrawalLog = (id, amt) -> {
                writer.logToFinalFile("Withdrawal: models.User " + id + " withdrew " + amt + ". New Balance: " + newBalance);
            };
            withdrawalLog.apply(userId, amount);

            UserService.updateUserInFile(currentUser);

            System.out.println("Withdrawal successful! New Balance: " + newBalance);

            List<User> updatedUsers = UserService.readUsersFromFile();
            updatedUsers.stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .map(user -> "ID: " + user.getUserId() + " | Name: " + user.getUsername() + " | Balance: " + user.getBalance())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }

    private User getUserById(String userId) throws IOException {
        List<User> users = UserService.readUsersFromFile();
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }
}
