package com.g2.tiptopG2.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound() {
        return "404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalError() {
        return "404";
    }
}