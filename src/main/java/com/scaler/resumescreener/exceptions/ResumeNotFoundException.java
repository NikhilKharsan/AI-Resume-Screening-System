package com.scaler.resumescreener.exceptions;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(String message) {
        super(message);
    }
}