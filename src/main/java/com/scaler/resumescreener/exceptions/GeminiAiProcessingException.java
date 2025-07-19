package com.scaler.resumescreener.exceptions;

public class GeminiAiProcessingException extends RuntimeException {
    public GeminiAiProcessingException(String message) {
        super(message);
    }

    public GeminiAiProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}