package com.fundoo.user.exception;

import com.fundoo.user.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class FundooExceptionHandler {

    @ExceptionHandler(value = RegistrationException.class)
    public ResponseEntity<Object> exceptionHandler(RegistrationException registrationException) {
        return new ResponseEntity<>(new ResponseDto(registrationException.message, registrationException.statusCode), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> methodNotSupported(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(new ResponseDto(e.getMessage(), 405), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LoginUserException.class)
    public ResponseEntity<Object> loginExceptionHandler(LoginUserException loginException) {
        return new ResponseEntity<>(new ResponseDto(loginException.getMessage(), 400), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ResponseDto(e.getBindingResult().getFieldError().getDefaultMessage(), 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Object> mediaTypeNotSupportExceptionHandler(HttpMediaTypeNotSupportedException e) {
        return new ResponseEntity<>(new ResponseDto("Unsupported Media Type", 400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleFileSizeLimitExceeded(MaxUploadSizeExceededException exc) {
        return new ResponseEntity<>(new ResponseDto("File Size Should be less than 10 MB", 400), HttpStatus.BAD_REQUEST);
    }

}
