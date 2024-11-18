package utilitiees;

import interfaces.DepositOperations;
import models.User;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class UserService {
    private static final String INFO_FILE = "info.txt";
    public static List<User> readUsersFromFile() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(INFO_FILE);


        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(INFO_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {

                    try {
                        User user = new User(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
                        users.add(user);
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing balance for user: " + parts[0]);
                    }
                }
            }
        }
        return users;
    }

    public static void writeUserToFile(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFO_FILE, true))) {
            writer.write(user.toString() + "\n");
        }
    }


    public static void updateUserInFile(User updatedUser) throws IOException {
        List<User> users = readUsersFromFile();

        users = users.stream()
                .filter(user -> !user.getUserId().equals(updatedUser.getUserId()))
                .collect(Collectors.toList());
        users.add(updatedUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFO_FILE))) {
            for (User user : users) {
                writer.write(user.toString() + "\n");
            }
        }
    }

    public static class DepositService {
        private Write writer;


        public DepositService(Write writer) {
            this.writer = writer;
        }


        public void deposit(String userId, double amount) {
            try {
                if (amount <= 0) {
                    System.out.println("Invalid deposit amount. It must be greater than zero.");
                    return;
                }


                Optional<User> optionalUser = getUserById(userId);
                if (!optionalUser.isPresent()) {
                    System.out.println("models.User not found. Deposit operation failed.");
                    return;
                }

                User currentUser = optionalUser.get();


                double newBalance = currentUser.getBalance() + amount;
                currentUser.setBalance(newBalance);

                DepositOperations depositLog = (id, amt) -> {
                    try {
                        writer.logToFinalFile("Deposit: models.User " + id + " deposited " + amt + ". New Balance: " + newBalance);
                    } catch (IOException e) {
                        System.out.println("Error logging deposit: " + e.getMessage());
                    }
                };
                depositLog.apply(userId, amount);


                try {
                    updateUserInFile(currentUser);
                } catch (IOException e) {
                    System.out.println("Error updating user file: " + e.getMessage());
                }


                System.out.println("Deposit successful! New Balance: " + newBalance);

                printUserDetails(userId);

            } catch (IOException e) {
                System.out.println("Error during deposit: " + e.getMessage());
            }
        }

        private Optional<User> getUserById(String userId) throws IOException {
            List<User> users = readUsersFromFile();
            return users.stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .findFirst();
        }


        private void printUserDetails(String userId) throws IOException {
            List<User> updatedUsers = readUsersFromFile();
            updatedUsers.stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .map(user -> "ID: " + user.getUserId() + " | Name: " + user.getUsername() + " | Balance: " + user.getBalance())
                    .forEach(System.out::println);  // Print updated user info after deposit
        }
    }
}
