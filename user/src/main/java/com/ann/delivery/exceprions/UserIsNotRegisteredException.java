package com.ann.delivery.exceprions;


public class UserIsNotRegisteredException extends RuntimeException{

    public UserIsNotRegisteredException(String message) {
        super(message);
    }
}
