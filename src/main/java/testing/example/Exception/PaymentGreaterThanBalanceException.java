package testing.example.Exception;

public class PaymentGreaterThanBalanceException  extends Exception {
    public PaymentGreaterThanBalanceException(String message) {
        super(message);
    }
}
