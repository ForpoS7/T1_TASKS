package t1.homeworks.synthetichumancore.util.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import t1.homeworks.synthetichumancore.exception.QueueOverflowException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(QueueOverflowException.class)
    public ResponseEntity<String> handleQueueOverflowException(QueueOverflowException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }
}
