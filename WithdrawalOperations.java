import java.io.IOException;

@FunctionalInterface
public interface WithdrawalOperations {
    void apply(String userId, double amount) throws IOException;
}
