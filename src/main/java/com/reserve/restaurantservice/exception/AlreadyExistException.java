package com.reserve.restaurantservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.ALREADY_REPORTED)
public class AlreadyExistException extends RuntimeException {
    private String message;
    public AlreadyExistException(){}
    public AlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}