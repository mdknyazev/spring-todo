package com.knzv.spring_boot_todo.exception;

import lombok.Getter;

@Getter
public class TaskNotExistsException extends RuntimeException{
    private final String message;
    public TaskNotExistsException(String message) {
        super();
        this.message = message;
    }
}
