package iss.nus.serverjasper.exceptions;

public class TransactionFailedException extends RuntimeException {

    public TransactionFailedException(String message) {
        super(message);
    }

    public TransactionFailedException(Throwable cause) {
        super(cause);
    }

    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
