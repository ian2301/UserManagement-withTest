package com.example.exception;

import org.springframework.stereotype.Service;


public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String s) {
        super(s);
    }

    public UserNotFoundException(String s, Throwable throwable) {
        super((s));
    }
}
