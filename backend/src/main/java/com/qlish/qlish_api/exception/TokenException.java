package com.qlish.qlish_api.exception;

public class TokenException extends RuntimeException{

    public TokenException(String message){
        super(message);
    }

    public TokenException(String message, Throwable cause){
        super(message, cause);
    }
}