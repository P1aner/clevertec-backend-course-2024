package ru.clevertec.session_storage.exception;

public class ResourceIsExists extends RuntimeException {
    public ResourceIsExists(String message) {
        super(message);
    }
}
