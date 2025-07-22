package t1.homeworks.tokenauth.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import t1.homeworks.tokenauth.dto.ApiResponse;
import t1.homeworks.tokenauth.exception.InvalidRefreshTokenException;
import t1.homeworks.tokenauth.exception.PasswordMismatchException;
import t1.homeworks.tokenauth.exception.UserAlreadyExistException;
import t1.homeworks.tokenauth.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        logger.warn("Validation error: {}", e.getMessage());
        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                "Ошибка валидации",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<String>> handleUserAlreadyExistException(UserAlreadyExistException e) {
        logger.warn("User already exists: {}", e.getMessage());
        ApiResponse<String> response = new ApiResponse<>(
                "Ошибка регистрации пользователя",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handlePasswordMismatchException(PasswordMismatchException e) {
        logger.warn("Password mismatch: {}", e.getMessage());
        ApiResponse<String> response = new ApiResponse<>(
                "Ошибка регистрации пользователя",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleUserNotFoundException(UserNotFoundException e) {
        logger.error("User not found: {}", e.getMessage());
        ApiResponse<String> response = new ApiResponse<>(
                "Ошибка идентификации",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiResponse<String>> handleTokenExpiredException(TokenExpiredException e) {
        logger.warn("Token expired: {}", e.getMessage());
        ApiResponse<String> response = new ApiResponse<>(
                "Ошибка аутентификации",
                "Срок действия токена истек, обновите токен"
        );

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<ApiResponse<String>> handleJWTVerificationException(JWTVerificationException e) {
        logger.error("JWT verification failed: {}", e.getMessage());
        ApiResponse<String> response = new ApiResponse<>(
                "Ошибка аутентификации",
                "Недействительный токен"
        );

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        logger.error("Invalid refresh token: {}", e.getMessage());
        ApiResponse<String> response = new ApiResponse<>(
                "Ошибка аутентификации",
                e.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
