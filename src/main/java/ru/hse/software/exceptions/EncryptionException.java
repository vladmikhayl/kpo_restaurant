package ru.hse.software.exceptions;

public class EncryptionException extends RuntimeException {
    public EncryptionException(String message, Exception ex) {
        super(message, ex);
    }
}
