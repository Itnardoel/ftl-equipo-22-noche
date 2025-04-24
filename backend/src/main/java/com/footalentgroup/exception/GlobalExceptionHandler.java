package com.footalentgroup.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String error, String message, Integer code, List<String> errors) {
        public ErrorResponse(Exception ex, Integer code) {
            this(ex.getClass().getSimpleName(), ex.getMessage(), code, List.of());
        }

        public ErrorResponse(String error, String message, Integer code) {
            this(error, message, code, List.of());
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public void unauthorized(Exception ex) {
        // Empty, nothing to do
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse unauthorized(BadCredentialsException ex) {
        return new ErrorResponse(
                "BadCredentialsException",
                "Nombre de usuario o contraseña incorrectos.",
                HttpStatus.UNAUTHORIZED.value()
        );
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({
            ForbiddenException.class,
            org.springframework.security.access.AccessDeniedException.class,
    })
    public ErrorResponse forbidden(Exception ex) {
        return new ErrorResponse(
                "ForbiddenException",
                "No tienes permiso para acceder a este recurso.",
                HttpStatus.FORBIDDEN.value()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BadRequestException.class,
            org.springframework.dao.DuplicateKeyException.class,
            org.springframework.http.converter.HttpMessageNotReadableException.class,
            org.springframework.web.server.ServerWebInputException.class
    })
    public ErrorResponse badRequest(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse badRequest(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return new ErrorResponse(
                "ValidationError",
                "La solicitud contiene datos inválidos.",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({
            ConflictException.class,
            DataIntegrityViolationException.class,
    })
    public ErrorResponse conflict(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.CONFLICT.value());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            NoResourceFoundException.class,
            ResponseStatusException.class,
            NoHandlerFoundException.class,
    })
    public ErrorResponse noResourceFoundRequest(Exception ex) {
        return new ErrorResponse(
                "NoResourceFoundException",
                "El recurso solicitado no existe. Itenta con `**/swagger-ui.html`",
                HttpStatus.NOT_FOUND.value()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse notFound(Exception ex) {
        return new ErrorResponse(ex, HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)   // The error must be corrected
    @ExceptionHandler(Exception.class)
    public ErrorResponse exception(Exception ex) {
        ex.printStackTrace();
        return new ErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
