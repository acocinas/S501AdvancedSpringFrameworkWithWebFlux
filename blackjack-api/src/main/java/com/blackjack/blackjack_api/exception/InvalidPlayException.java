package com.blackjack.blackjack_api.exception;

public class InvalidPlayException extends RuntimeException {
    public InvalidPlayException(String message) {
        super(message);
    }
}