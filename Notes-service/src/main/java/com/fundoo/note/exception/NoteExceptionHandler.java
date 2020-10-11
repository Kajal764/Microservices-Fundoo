package com.fundoo.note.exception;

import com.fundoo.label.exception.LabelException;
import com.fundoo.note.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoteExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> authorizationException(AuthenticationException authenticationException){
        return new ResponseEntity<>(new ResponseDto(authenticationException.getMessage(),403),HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = NoteException.class)
    public ResponseEntity<Object> noteException(NoteException noteException){
        return new ResponseEntity<>(new ResponseDto(noteException.getMessage(),400),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = LabelException.class)
    public ResponseEntity<Object> labelException(LabelException labelException){
        return new ResponseEntity<>(new ResponseDto(labelException.getMessage(),400),HttpStatus.FORBIDDEN);
    }
}
