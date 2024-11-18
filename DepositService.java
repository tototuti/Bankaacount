import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DepositService {
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
                System.out.println("User not found. Deposit operation failed.");
                return;
            }

            User currentUser = optionalUser.get();


            double newBalance = currentUser.getBalance() + amount;
            currentUser.setBalance(newBalance);

            DepositOperations depositLog = (id, amt) -> {
                try {
                    writer.logToFinalFile("Deposit: User " + id + " deposited " + amt + ". New Balance: " + newBalance);
                } catch (IOException e) {
                    System.out.println("Error logging deposit: " + e.getMessage());
                }
            };
            depositLog.apply(userId, amount);


            try {
                UserService.updateUserInFile(currentUser);
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
        List<User> users = UserService.readUsersFromFile();
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }


    private void printUserDetails(String userId) throws IOException {
        List<User> updatedUsers = UserService.readUsersFromFile();
        updatedUsers.stream()
                .filter(user -> user.getUserId().equals(userId))
                .map(user -> "ID: " + user.getUserId() + " | Name: " + user.getUsername() + " | Balance: " + user.getBalance())
                .forEach(System.out::println);  // Print updated user info after deposit
    }
}
