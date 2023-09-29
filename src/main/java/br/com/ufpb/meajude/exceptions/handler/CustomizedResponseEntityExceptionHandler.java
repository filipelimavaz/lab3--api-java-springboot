package br.com.ufpb.meajude.exceptions.handler;

import br.com.ufpb.meajude.dtos.errors.ErrorDetailsDTO;
import br.com.ufpb.meajude.exceptions.InvalidFieldException;
import br.com.ufpb.meajude.exceptions.InvalidRequestException;
import br.com.ufpb.meajude.exceptions.NotFoundException;
import br.com.ufpb.meajude.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTitle(exception.getTitle());
        error.setType(URI);
        error.setDetail(exception.getDetails());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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
}
