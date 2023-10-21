package br.dcx.ufpb.meajude.exceptions.handler;

import br.dcx.ufpb.meajude.dtos.errors.ErrorDetailsDTO;
import br.dcx.ufpb.meajude.exceptions.*;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static String URI = "https://localhost:8080/api/campaigns";

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> NotFoundException(NotFoundException exception) {
        ErrorDetailsDTO error = new ErrorDetailsDTO();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle(exception.getTitle());
        error.setType(URI);
        error.setDetail(exception.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ErrorDetailsDTO> InvalidFieldException(InvalidFieldException exception) {
        ErrorDetailsDTO error = new ErrorDetailsDTO();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle(exception.getTitle());
        error.setType(URI);
        error.setDetail(exception.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDetailsDTO> UnauthorizedException(UnauthorizedException exception) {
        ErrorDetailsDTO error = new ErrorDetailsDTO();
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setTitle(exception.getTitle());
        error.setType(URI);
        error.setDetail(exception.getDetails());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorDetailsDTO> InvalidRequestException(InvalidRequestException exception) {
        ErrorDetailsDTO error = new ErrorDetailsDTO();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle(exception.getTitle());
        error.setType(URI);
        error.setDetail(exception.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<List<ErrorDetailsDTO>> handleCustomValidationException(CustomValidationException exception) {
        List<ErrorDetailsDTO> errors = new ArrayList<>();

        for (ConstraintViolation<Object> violation : exception.getViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();

            ErrorDetailsDTO error = new ErrorDetailsDTO();
            error.setStatus(HttpStatus.BAD_REQUEST.value());
            error.setTitle("Invalid field error: " + fieldName);
            error.setType(URI);
            error.setDetail(errorMessage);

            errors.add(error);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
