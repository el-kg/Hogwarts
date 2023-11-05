package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(StudentAlreadyExistException.class)
    public ResponseEntity<String> handelException(StudentAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handelNotFoundException(StudentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(FacultyAlreadyExistException.class)
    public ResponseEntity<String> handelException(FacultyAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public ResponseEntity<String> handelNotFoundException(FacultyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
