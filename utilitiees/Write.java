package utilitiees;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Write {
    public static final String INFO_FILE = "info.txt";
    public static final String FINAL_FILE = "final.txt";


    public Map<String, String[]> readAllAccounts() throws IOException {
        Map<String, String[]> userAccounts = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INFO_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(",");
                if (userInfo.length == 4) {
                    userAccounts.put(userInfo[0], userInfo);
                }
            }
        }
        return userAccounts;
    }


    public void writeAllAccounts(Map<String, String[]> accounts) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFO_FILE))) {
            for (String[] userInfo : accounts.values()) {
                writer.write(String.join(",", userInfo) + "\n");
            }
        }
    }

    public void logToFinalFile(String log) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FINAL_FILE, true))) {
            writer.write(log + "\n");
        }
    }


    public void updateUserAccount(String userId, String[] updatedInfo) throws IOException {
        Map<String, String[]> userAccounts = readAllAccounts();


        if (userAccounts.containsKey(userId)) {
            userAccounts.put(userId, updatedInfo);
            writeAllAccounts(userAccounts);  // Save the updated data back to the file
        } else {
            throw new IOException("models.User with ID " + userId + " not found.");
        }
    }
}
