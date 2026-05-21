package br.com.estacioneja.errors;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.estacioneja.errors.exceptions.BusinessException;
import br.com.estacioneja.errors.exceptions.ConectionIsDownException;
import br.com.estacioneja.errors.exceptions.CustomMessageException;
import br.com.estacioneja.errors.exceptions.DuplicateCompanyException;
import br.com.estacioneja.errors.exceptions.DuplicateException;
import br.com.estacioneja.errors.exceptions.DuplicatePlateException;
import br.com.estacioneja.errors.exceptions.EntityNotFoundException;
import br.com.estacioneja.errors.exceptions.ForbiddenException;
import br.com.estacioneja.errors.exceptions.ParkIsPrivateException;
import br.com.estacioneja.errors.exceptions.ParkSizeViolatedException;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
        EntityNotFoundException.class
    })
    private ResponseEntity<ApiErrorResponse> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
        ParkSizeViolatedException.class,
        ParkIsPrivateException.class,
        BusinessException.class
    })
    private ResponseEntity<ApiErrorResponse> handleBadRequest(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
        DuplicateException.class,
        DuplicateCompanyException.class,
        DuplicatePlateException.class
    })
    private ResponseEntity<ApiErrorResponse> handleDuplicate(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
        ConectionIsDownException.class
    })
    private ResponseEntity<ApiErrorResponse> handleConection(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
        CustomMessageException.class
    })
    private ResponseEntity<ApiErrorResponse> handleCustomMessage(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(new ApiErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
        ForbiddenException.class
    })
    private ResponseEntity<ApiErrorResponse> handleForbidden(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiErrorResponse(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            org.springframework.http.HttpStatusCode status,
            org.springframework.web.context.request.WebRequest request) {

        List<ValidationFieldError> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationFieldError::new)
                .toList();

        return ResponseEntity.badRequest().body(erros);
    }
}
