package br.com.estacioneja.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.estacioneja.dto.output.ResponseExceptionDTO;
import br.com.estacioneja.exceptions.custom.AccessNotFoundException;
import br.com.estacioneja.exceptions.custom.CompanyNotFoundException;
import br.com.estacioneja.exceptions.custom.ParkIsFullException;
import br.com.estacioneja.exceptions.custom.ParkNotFoundException;
import br.com.estacioneja.exceptions.custom.ReservationNotFoundException;
import br.com.estacioneja.exceptions.custom.TimeIsNotAvailableException;
import br.com.estacioneja.exceptions.custom.UserNotFoundException;
import br.com.estacioneja.exceptions.custom.VacancyIsNotAvailableException;
import br.com.estacioneja.exceptions.custom.VacancyNotFoundException;
import br.com.estacioneja.exceptions.custom.VeicleNotFoundException;
import br.com.estacioneja.exceptions.custom.VincleNotFoundException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
        UserNotFoundException.class,
        CompanyNotFoundException.class,
        ParkNotFoundException.class,
        VacancyNotFoundException.class,
        VeicleNotFoundException.class,
        AccessNotFoundException.class,
        ReservationNotFoundException.class,
        VincleNotFoundException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler({
        TimeIsNotAvailableException.class,
        VacancyIsNotAvailableException.class
    })
    private ResponseEntity<ResponseExceptionDTO> handleNotAvailable(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseExceptionDTO(exception.getMessage()));
    }

    @ExceptionHandler(ParkIsFullException.class)
    private ResponseEntity<ResponseExceptionDTO> handleFull(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseExceptionDTO(exception.getMessage()));

    }

}
