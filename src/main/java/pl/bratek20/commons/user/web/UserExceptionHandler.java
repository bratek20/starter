package pl.bratek20.commons.user.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.bratek20.commons.user.api.exceptions.UserAlreadyExistsException;
import pl.bratek20.commons.user.api.exceptions.UserNotExistsException;
import pl.bratek20.commons.user.api.exceptions.WrongUserPasswordException;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
        UserAlreadyExistsException.class,
        WrongUserPasswordException.class,
        UserNotExistsException.class
    })
    public ResponseEntity<String> handleException(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}