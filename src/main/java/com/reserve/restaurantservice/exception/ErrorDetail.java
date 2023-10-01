package com.reserve.restaurantservice.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Data
@NoArgsConstructor
public class ErrorDetail {
    private String errorMessage;
    private HttpStatus errorCode;
    private Date timeStamp;


    public ErrorDetail(String errorMessage, HttpStatus errorCode, Date timeStamp) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.timeStamp = timeStamp;
    }
}

