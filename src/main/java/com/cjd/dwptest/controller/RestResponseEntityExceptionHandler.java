package com.cjd.dwptest.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cjd.dwptest.exception.NoEndpointFoundException;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    static final String DESCRIPTION_KEY = "description";
    static final String EXCEPTION_KEY = "exception";
    static final String MESSAGE_KEY = "message";    

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgument(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, getBody("Incorrect query parameter value", ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, getBody("Incorrect query parameter type", ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NoEndpointFoundException.class })
    protected ResponseEntity<Object> handleNoEndpointFound (RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,  getBody("Failed to reach wrapped endpoint", ex), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({Exception.class })
    protected ResponseEntity<Object> handleAllOtherExceptions (RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, getBody("Something went wrong", ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private Map<String, Object> getBody(String description, Exception ex) {
        Map<String, Object> map = new HashMap<>();

        map.put(DESCRIPTION_KEY, description);
        map.put(EXCEPTION_KEY, ex.getClass().getName());
        map.put(MESSAGE_KEY, ex.getMessage());
        
        return map;
    }

}
