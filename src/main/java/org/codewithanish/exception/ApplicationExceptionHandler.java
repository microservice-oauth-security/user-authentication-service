package org.codewithanish.exception;

import org.codewithanish.vo.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException exception)
    {
        return new ResponseEntity<>(new ErrorResponse("User already exists"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exception)
    {
        return new ResponseEntity<>(new ErrorResponse("User does not exist"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleException(AuthenticationException exception)
    {
        return new ResponseEntity<>(new ErrorResponse("Invalid credentials"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception)
    {
        return new ResponseEntity<>(new ErrorResponse("Oops!!! something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
