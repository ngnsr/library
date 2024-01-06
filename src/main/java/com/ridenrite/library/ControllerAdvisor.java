package com.ridenrite.library;

import com.ridenrite.library.exception.ConflictException;
import com.ridenrite.library.exception.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatusCode status,
                                                                  @NotNull WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();
        Map<String, Object> body = Map.of(
                "timestamp" , new Date(),
                "status", status,
                "errors", errors
        );
        return new ResponseEntity<>(body, headers, status);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex){
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.NOT_FOUND,
                "errors", List.of(ex.getMessage())
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex){
        Map<String, Object> body = Map.of(
                "timestamp", new Date(),
                "status", HttpStatus.CONFLICT,
                "errors", List.of(ex.getMessage())
        );
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

}
