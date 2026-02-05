package com.cris959.SupermarketApi.exception;

public class NoResultsFoundException extends RuntimeException{
    public NoResultsFoundException(String message) {
        super(message);
    }
}
