package com.knzv.spring_boot_todo.exception;


import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException{
    private final String message;
    public UserAlreadyExistsException(String message) {
        super();
        this.message = message;
    }
}
