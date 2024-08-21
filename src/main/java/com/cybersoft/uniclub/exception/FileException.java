package com.cybersoft.uniclub.exception;

import com.cybersoft.uniclub.response.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;



@Data
@AllArgsConstructor
public class FileException extends RuntimeException{
    private String message;



}
