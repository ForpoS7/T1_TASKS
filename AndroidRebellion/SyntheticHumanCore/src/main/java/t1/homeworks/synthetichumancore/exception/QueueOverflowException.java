package t1.homeworks.synthetichumancore.exception;

public class QueueOverflowException extends RuntimeException {
    public QueueOverflowException(String message) {
        super(message);
    }
    public QueueOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
