package com.cybersoft.uniclub.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenException extends RuntimeException{
    private String message;
}
