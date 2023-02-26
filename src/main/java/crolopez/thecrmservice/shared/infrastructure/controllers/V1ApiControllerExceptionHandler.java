package crolopez.thecrmservice.shared.infrastructure.controllers;

import crolopez.thecrmservice.shared.domain.entities.dto.ErrorDto;
import crolopez.thecrmservice.shared.domain.entities.dto.GenericResponseDto;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

@RestControllerAdvice
@Log4j2
public class V1ApiControllerExceptionHandler {
    private final String UNEXPECTED_ERROR_MESSAGE = "Unexpected API error";
    private final String UNEXPECTED_PERSISTENCE_ERROR_MESSAGE = "Unexpected persistence error";
    private final String UNEXPECTED_HTTP_ERROR_MESSAGE = "Unexpected error processing HTTP request";
    private final String ENTITY_NOT_FOUND_ERROR_MESSAGE = "Entity not found";
    private final String INVALID_REQUEST_ERROR_MESSAGE = "Invalid request";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Invalid request: {}", ex.getMessage());
        GenericResponseDto response =new GenericResponseDto()
                .message(INVALID_REQUEST_ERROR_MESSAGE)
                .errors(ex.getFieldErrors().stream().map(x ->
                        new ErrorDto()
                                .field(x.getField())
                                .error(x.getDefaultMessage())
                ).toList());
        return new ResponseEntity(response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GenericResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        IllegalArgumentException illegalArgumentException = getExceptionCause(ex, IllegalArgumentException.class);

        if (illegalArgumentException != null) {
            String argumentError = illegalArgumentException.getMessage();
            log.error("Illegal argument error: {}", argumentError);
            return new ResponseEntity(new GenericResponseDto().message(argumentError),
                    HttpStatus.BAD_REQUEST);
        }

        log.error("Could not process the http request: {}", ex.getMessage());
        return new ResponseEntity(new GenericResponseDto().message(UNEXPECTED_HTTP_ERROR_MESSAGE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<GenericResponseDto> handleRollbackException(RollbackException ex) {
        ConstraintViolationException constraintException = getExceptionCause(ex, ConstraintViolationException.class);

        if (constraintException != null) {
            String constraintError = constraintException.getSQLException().getMessage();
            log.error("Constraint violation error: {}", constraintError);
            return new ResponseEntity(new GenericResponseDto().message(constraintError),
                    HttpStatus.CONFLICT);
        }

        log.error("Could not process the persistence operation: {}", ex.getMessage());
        return new ResponseEntity(new GenericResponseDto().message(UNEXPECTED_PERSISTENCE_ERROR_MESSAGE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GenericResponseDto> handleEntityNotFoundException(Exception ex) {
        log.error("Entity not found: {}", ex.getMessage());
        return new ResponseEntity(new GenericResponseDto().message(ENTITY_NOT_FOUND_ERROR_MESSAGE),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto> handleGenericException(Exception ex) {
        log.error("Unexpected exception: {}", ex.getMessage());
        return new ResponseEntity(new GenericResponseDto().message(UNEXPECTED_ERROR_MESSAGE),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <T extends  Throwable> T getExceptionCause(Throwable ex, Class<T> exceptionCauseClass) {
        final int MAX_DEEP = 5;
        Throwable cause = ex.getCause();
        for (int i = 0; i < MAX_DEEP; i++, cause = cause.getCause())
            if (cause == null)
                break;
            else if (cause.getClass() == exceptionCauseClass)
                return (T) cause;

        return null;
    }
}
