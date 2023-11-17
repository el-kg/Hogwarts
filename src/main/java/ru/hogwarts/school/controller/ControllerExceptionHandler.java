package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.*;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({StudentAlreadyExistException.class,FacultyAlreadyExistException.class})
    public ResponseEntity<String> handelException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler({StudentNotFoundException.class,FacultyNotFoundException.class, AvatarNotFoundException.class})
    public ResponseEntity<String> handelNotFoundException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
