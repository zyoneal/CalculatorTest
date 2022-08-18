package com.viktor.calculator.exception;

public class OperationException extends RuntimeException{

    public OperationException() {
        super();
    }

    public OperationException(String message) {
        super(message);
    }

}
