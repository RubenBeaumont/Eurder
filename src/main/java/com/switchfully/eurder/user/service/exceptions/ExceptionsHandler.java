package com.switchfully.eurder.user.service.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    protected void notFoundException(NotFoundException exception, HttpServletResponse response) throws IOException{
        response.sendError(NOT_FOUND.value(), exception.getMessage());
    }
    @ExceptionHandler(InvalidParametersException.class)
    protected void invalidParametersException(InvalidParametersException exception, HttpServletResponse response) throws IOException{
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected void unauthorizedException(UnauthorizedException exception, HttpServletResponse response) throws IOException{
        response.sendError(UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(WrongPasswordException.class)
    protected void wrongPasswordException(WrongPasswordException exception, HttpServletResponse response)throws IOException{
        response.sendError(BAD_REQUEST.value(), exception.getMessage());
    }
}
