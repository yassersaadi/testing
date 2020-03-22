package testing.example.Exception;

public class NegativePaymentException extends Exception {
    public NegativePaymentException(String message) {
        super(message);
    }
}
