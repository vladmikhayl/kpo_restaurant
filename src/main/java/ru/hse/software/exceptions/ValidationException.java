package ru.hse.software.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message, Exception ex) {
        super(message, ex);
    }
}
