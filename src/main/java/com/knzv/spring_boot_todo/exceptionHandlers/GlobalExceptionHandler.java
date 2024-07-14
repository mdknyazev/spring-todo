package com.knzv.spring_boot_todo.exceptionHandlers;

import com.knzv.spring_boot_todo.dto.ErrorResponse;
import com.knzv.spring_boot_todo.exception.InvalidTaskException;
import com.knzv.spring_boot_todo.exception.TaskNotExistsException;
import com.knzv.spring_boot_todo.exception.UserAlreadyExistsException;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TaskNotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse taskNotExistsExceptionHandler(TaskNotExistsException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse userAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(value = InvalidTaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse invalidTaskExceptionHandler(InvalidTaskException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
