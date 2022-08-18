package com.viktor.calculator.exceptions;

public class OperationException extends RuntimeException {

    public OperationException() {
        super();
    }

    public OperationException(String message) {
        super(message);
    }

}
