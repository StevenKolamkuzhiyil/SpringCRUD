package com.stevenkolamkuzhiyil.SpringCrud.exception;

import com.stevenkolamkuzhiyil.SpringCrud.exception.custom.ApiException;
import com.stevenkolamkuzhiyil.SpringCrud.exception.custom.InvalidObjectException;
import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.BranchNotFound;
import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.EmployeeNotFound;
import com.stevenkolamkuzhiyil.SpringCrud.exception.throwable.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(value = {EmployeeNotFound.class, BranchNotFound.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<?> entityNotFound(RuntimeException ex, HttpServletRequest request) {
        ApiException apiException = new ApiException(ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiException);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> validationFailedHandler(ValidationException ex, HttpServletRequest request) {
        InvalidObjectException invalidObjectException = new InvalidObjectException(ZonedDateTime.now(ZoneId.of("Z")),
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                request.getRequestURI(),
                ex.getBindingResult());

        return ResponseEntity.badRequest().body(invalidObjectException);
    }

}
