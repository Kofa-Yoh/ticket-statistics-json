package com.kotkina.errors;

public class NoSuchTicketException extends RuntimeException {

    public NoSuchTicketException(String message) {
        super(message);
    }
}
