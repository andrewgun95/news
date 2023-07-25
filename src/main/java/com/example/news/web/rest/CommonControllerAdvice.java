package com.example.news.web.rest;

import com.example.news.web.rest.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse error = new ErrorResponse(
                httpStatus,
                ex.getLocalizedMessage(),
                httpStatus.value()
        );
        return new ResponseEntity<>(error, httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void resolveAccessDeniedException(AccessDeniedException ex) {
        log.warn("Attempted to access an endpoint with insufficient privileges", ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public void resolveAuthenticationException(AccessDeniedException ex) {
        log.warn("Attempted to access an endpoint with wrong credentials", ex);
    }

}
