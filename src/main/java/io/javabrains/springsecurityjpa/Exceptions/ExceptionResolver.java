package io.javabrains.springsecurityjpa.Exceptions;

import io.javabrains.springsecurityjpa.Beans.StatusBean;
import io.javabrains.springsecurityjpa.JWT.JwtRequestFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler
    public StatusBean handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request){

        return new StatusBean(0,"failed",e.getMessage());
    }
}
