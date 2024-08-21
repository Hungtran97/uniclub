package com.cybersoft.uniclub.exception;

import com.cybersoft.uniclub.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<?> handlingRuntimeException (RuntimeException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<?> handlingValidtionException (MethodArgumentNotValidException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(value = AuthenException.class)
    ResponseEntity<?> handlingAuthenException (AuthenException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(FileException.class)
    public ResponseEntity<?> handleFileException(FileException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
