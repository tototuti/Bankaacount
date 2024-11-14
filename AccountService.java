import java.io.IOException;

public class AccountService {
    private Write writer;

    public AccountService() {
        this.writer = new Write();
    }

    public boolean confirmPassword(String enteredPassword) {
        try {
            String[] accountInfo = writer.readAccountFromFile();
            return enteredPassword.equals(accountInfo[2]);
        } catch (IOException e) {
            System.out.println("Error reading file to confirm password: " + e.getMessage());
            return false;
        }
    }

    public String[] readAccountFromFile() throws IOException {
        return writer.readAccountFromFile();
    }

    public void writeAccountToFile(String name, double balance, String password) throws IOException {
        writer.writeAccountToFile(name, balance, password);
    }
}
