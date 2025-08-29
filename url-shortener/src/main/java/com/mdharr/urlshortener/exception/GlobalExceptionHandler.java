package com.mdharr.urlshortener.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HashUnknown.class)
    public ModelAndView handleHashUnknownException() {
        return new ModelAndView("redirect:/");
    }
}
