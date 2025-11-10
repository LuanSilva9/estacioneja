package br.com.estacioneja.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.estacioneja.dto.output.ResponseExceptionDTO;
import br.com.estacioneja.exceptions.custom.ConectionIsDownException;
import br.com.estacioneja.exceptions.custom.CustomMessageException;
import br.com.estacioneja.exceptions.custom.DuplicateCompanyException;
import br.com.estacioneja.exceptions.custom.DuplicatePlateException;
import br.com.estacioneja.exceptions.custom.DuplicateException;
import br.com.estacioneja.exceptions.custom.EntityNotFoundException;
import br.com.estacioneja.exceptions.custom.ForbiddenException;
import br.com.estacioneja.exceptions.custom.ParkSizeViolatedException;
import br.com.estacioneja.exceptions.custom.ParkIsPrivateException;
import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
        EntityNotFoundException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler({
        ParkSizeViolatedException.class,
        ParkIsPrivateException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleFull(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler({
        DuplicateException.class,
        DuplicateCompanyException.class,
        DuplicatePlateException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleDuplicate(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler({
        ConectionIsDownException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleConection(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler({
        CustomMessageException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleCustomMessage(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler({
        ForbiddenException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleForbidden(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseExceptionDTO(exception.getMessage()));
    }
}
