package com.cjd.dwptest.exception;

public class NoEndpointFoundException extends RuntimeException{

    public NoEndpointFoundException() {
        super();
    }

    public NoEndpointFoundException(String errorMessage) {
        super(errorMessage);
    }
}