package com.knzv.spring_boot_todo.exception;

import lombok.Getter;

@Getter
public class InvalidTaskException extends RuntimeException{
    private String message;
    public InvalidTaskException() {}
    public InvalidTaskException(String message) {
        super();
        this.message = message;
    }
}
