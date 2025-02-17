package com.qlish.qlish_api.exceptions;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message){
        super(message);
    }
}
