package com.footalentgroup.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String message,HttpStatus status, List<String> errors) {
        public ErrorResponse(String message, HttpStatus status) {
            this(message, status, null);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse("Validación fallida", HttpStatus.BAD_REQUEST, errores);
        return new ResponseEntity<>(errorResponse, errorResponse.status());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, errorResponse.status());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = Objects.requireNonNull(ex.getRootCause()).getMessage();
        ErrorResponse errorResponse = new ErrorResponse(message, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, errorResponse.status());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Credenciales inválidas",HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, errorResponse.status());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<?> handlePropertyReferenceException(PropertyReferenceException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid parameter: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleException(EmailAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.CONFLICT
        );

        return new ResponseEntity<>(errorResponse, errorResponse.status());
    }

// Se crea una excepción personalizada y se maneja con un @ExceptionHandler.
// Se pasa la excepción como parámetro y se utiliza un DTO (ErrorResponse) para retornar
// un mensaje claro y el código HTTP correspondiente.

//    Ejemplo:
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> handleServiceException(UserNotFoundException ex){
//        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(errorResponse, errorResponse.status());
//    }
}
