package com.reserve.restaurantservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CantCreateException extends RuntimeException {
        private String message;
        public CantCreateException(){}
        public CantCreateException(String message) {
            super(message);
            this.message = message;
        }
}

