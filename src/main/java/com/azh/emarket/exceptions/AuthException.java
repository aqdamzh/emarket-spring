package com.azh.emarket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
