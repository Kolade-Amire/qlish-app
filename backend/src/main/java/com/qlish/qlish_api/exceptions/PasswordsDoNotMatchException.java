package com.qlish.qlish_api.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException {

    public PasswordsDoNotMatchException(String message){
        super(message);
    }
}
