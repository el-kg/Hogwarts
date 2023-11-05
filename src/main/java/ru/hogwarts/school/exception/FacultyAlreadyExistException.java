package ru.hogwarts.school.exception;

public class FacultyAlreadyExistException extends RuntimeException {
    public FacultyAlreadyExistException() {
    }

    public FacultyAlreadyExistException(String message) {
        super(message);
    }

    public FacultyAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FacultyAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public FacultyAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
