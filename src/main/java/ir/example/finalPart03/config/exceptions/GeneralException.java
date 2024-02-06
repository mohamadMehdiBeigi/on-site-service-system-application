package ir.example.finalPart03.config.exceptions;

import jakarta.persistence.NoResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException ex) {

        HttpStatus NotFound = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                NotFound
        );
        return new ResponseEntity<>(apiException, NotFound);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Object> noResultException(NoResultException ex) {
        HttpStatus NoResult = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                NoResult
        );
        return new ResponseEntity<>(apiException, NoResult);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> applicationException(BadRequestException ex) {
        HttpStatus BadRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                BadRequest
        );
        return new ResponseEntity<>(apiException, BadRequest);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> duplicateException(DuplicateException ex) {
        HttpStatus Duplicate = HttpStatus.CONFLICT;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                Duplicate
        );
        return new ResponseEntity<>(apiException, Duplicate);
    }
}
