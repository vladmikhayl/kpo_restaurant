package ru.hse.software.exceptions;

public class DataBaseSerializingException extends RuntimeException {
    public DataBaseSerializingException(String message, Exception ex) {
        super(message, ex);
    }
}
