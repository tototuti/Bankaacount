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
}
