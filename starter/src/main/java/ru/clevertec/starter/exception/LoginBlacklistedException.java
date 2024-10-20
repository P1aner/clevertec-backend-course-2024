package ru.clevertec.starter.exception;

public class LoginBlacklistedException extends RuntimeException {
    public LoginBlacklistedException(String message) {
        super(message);
    }
}
