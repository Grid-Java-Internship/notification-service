package com.internship.notification_service.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class NotificationExceptionHandler {

    private ResponseEntity<ExceptionResponse> buildErrorResponse(
            HttpStatus httpStatus,
            List<String> messages
    ) {
        messages.forEach(log::error);

        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .statusCode(httpStatus.value())
                .messages(messages)
                .build();

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(Exception ex) {

        List<String> errorMessages;

        if (ex instanceof ConstraintViolationException constraintViolationException) {
            errorMessages = constraintViolationException.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
        }
        else if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            errorMessages = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
        }
        else { // HttpMessageNotReadableException
            errorMessages = List.of(ex.getMessage());
        }

        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessages);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(
            NotFoundException ex
    ) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(UnknownUserIdException.class)
    public ResponseEntity<ExceptionResponse> handleUnknownUserId(
            UnknownUserIdException ex
    ){
        return buildErrorResponse(HttpStatus.FORBIDDEN, List.of(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(
            Exception ex
    ) {
        String errorMessage = "Request failed because of an internal problem. " +
                "Please contact support or your administrator. Error: " + ex.getMessage();

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, List.of(errorMessage));
    }
}
