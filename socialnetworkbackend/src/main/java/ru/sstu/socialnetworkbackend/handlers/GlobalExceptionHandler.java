package ru.sstu.socialnetworkbackend.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sstu.socialnetworkbackend.exceptions.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_MSG = "error";
    private static final String INCORRECT_USERNAME_OR_PASSWORD = "Неправильный логин или пароль";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler({
        PasswordsNotMatchException.class,
        ResourceAlreadyExistsException.class,
        EmptyFileException.class,
        IncorrectKeywordException.class,
        IncorrectSearchTermException.class,
        ExpiredTokenException.class,
        TokenAlreadyConfirmed.class,
        MaxFileSizeExceededException.class,
        IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, String>> handleBadRequest(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MSG, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException() {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MSG, INCORRECT_USERNAME_OR_PASSWORD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorized(Exception e) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MSG, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(ResourceNotFoundException e) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MSG, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, String> response = new HashMap<>();
        response.put(ERROR_MSG, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

}
