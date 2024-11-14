import java.io.*;

public class Write {
    public static final String FILE_PATH = "info.txt";

    public String[] readAccountFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
        String name = reader.readLine();
        String balance = reader.readLine();
        String password = reader.readLine();
        reader.close();
        return new String[]{name, balance, password};
    }

    public void writeAccountToFile(String name, double balance, String password) throws IOException {
        FileWriter writer = new FileWriter(FILE_PATH);
        writer.write(name + "\n");
        writer.write(balance + "\n");
        writer.write(password + "\n");
        writer.close();
    }
}
