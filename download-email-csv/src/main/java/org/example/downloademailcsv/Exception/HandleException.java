package org.example.downloademailcsv.Exception;

import jakarta.mail.AuthenticationFailedException;
import lombok.RequiredArgsConstructor;
import org.example.downloademailcsv.Dto.ApiResonseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor

public class HandleException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationFailedException.class})
    protected ResponseEntity<String> handleAuthenticationFailedException(AuthenticationFailedException ex) {
        return new  ResponseEntity<>("Authentication Failed!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
