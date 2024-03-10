package ru.hse.software.exceptions;

public class MenuParsingException extends RuntimeException {
    public MenuParsingException(String message, Exception ex) {
        super(message, ex);
    }
}
