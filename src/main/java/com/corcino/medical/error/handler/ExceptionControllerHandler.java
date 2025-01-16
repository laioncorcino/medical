package com.corcino.medical.error.handler;

import com.corcino.medical.error.StandardError;
import com.corcino.medical.error.ValidationError;
import com.corcino.medical.error.exception.BadRequestException;
import com.corcino.medical.error.exception.ForbiddenException;
import com.corcino.medical.error.exception.NotFoundException;
import com.corcino.medical.error.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFound(NotFoundException notFoundException) {

        StandardError error = StandardError.builder()
                .title("Object Not Found Exception. Check documentation.")
                .status(HttpStatus.NOT_FOUND.value())
                .errorMessage(notFoundException.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardError> handleBadRequest(BadRequestException badRequestException) {

        StandardError error = StandardError.builder()
                .title("Bad Request Exception.")
                .status(HttpStatus.BAD_REQUEST.value())
                .errorMessage(badRequestException.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnauthorizedException.class, AuthenticationException.class})
    public ResponseEntity<StandardError> handleUnauthorizedException(RuntimeException exception) {

        StandardError error = StandardError.builder()
                .title("API access unauthorized.")
                .status(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(exception.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    public ResponseEntity<StandardError> handleForbiddenException(RuntimeException exception) {

        StandardError error = StandardError.builder()
                .title("API access forbidden.")
                .status(HttpStatus.FORBIDDEN.value())
                .errorMessage(exception.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentException) {

        List<ValidationError> standardErrors = new ArrayList<>();
        List<FieldError> fieldErrors = methodArgumentException.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            standardErrors.add(ValidationError.builder()
                    .title("Bad Request Exception. Invalid fields.")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorMessage(fieldError.getDefaultMessage())
                    .dateTime(getDateTime())
                    .field(fieldError.getField())
                    .build()
            );

            log.error("Erro de validação no campo {} para se criar ou atualizar recurso", fieldError.getField());
        });

        return new ResponseEntity<>(standardErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleInternalException(Exception exception) throws Exception {

        StandardError error = StandardError.builder()
                .title("Internal exception in server")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(exception.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<StandardError> handleInternalError(Error err) {

        StandardError error = StandardError.builder()
                .title("Internal error in server ")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(err.getMessage())
                .dateTime(getDateTime())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }

}
