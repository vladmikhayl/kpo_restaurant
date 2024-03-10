package ru.hse.software.exceptions;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException(String message, Exception ex) {
        super(message, ex);
    }
}
