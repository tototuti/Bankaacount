@FunctionalInterface
public interface DepositOperations {
    void apply(String userId, double amount);
}
