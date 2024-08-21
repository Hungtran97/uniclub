package com.cybersoft.uniclub.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthenRequest(
        @Email(message ="This must be an email")
        @NotEmpty(message = "This field is not empty")
        String email,
        @NotEmpty(message = "This field is not empty")
        String password) {

}
