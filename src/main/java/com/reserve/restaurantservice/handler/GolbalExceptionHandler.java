package com.reserve.restaurantservice.handler;


import com.reserve.restaurantservice.exception.AlreadyExistException;
import com.reserve.restaurantservice.exception.CantCreateException;
import com.reserve.restaurantservice.exception.ErrorDetail;
import com.reserve.restaurantservice.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GolbalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetail> NotFoundException(NotFoundException ex, WebRequest req) {
        ErrorDetail error = new ErrorDetail(ex.getMessage(), HttpStatus.NOT_FOUND, new Date());
        return new ResponseEntity<ErrorDetail>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorDetail> AlreadyExistsException(AlreadyExistException ex, WebRequest req) {
        ErrorDetail error = new ErrorDetail(ex.getMessage(), HttpStatus.BAD_REQUEST , new Date());
        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
}
    @ExceptionHandler(CantCreateException.class)
    public ResponseEntity<ErrorDetail> CantReserveException(CantCreateException ex, WebRequest req) {
        ErrorDetail error = new ErrorDetail(ex.getMessage(), HttpStatus.BAD_REQUEST, new Date());
        return new ResponseEntity<ErrorDetail>(error, HttpStatus.BAD_REQUEST);
    }}
