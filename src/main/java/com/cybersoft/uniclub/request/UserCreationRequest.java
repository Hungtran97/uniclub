package com.cybersoft.uniclub.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreationRequest(@Email(message = "Email field must is an email") String email,
                                  @Size(min = 6, message = "Password must at least 6 character") String password,
                                  @NotNull(message = "Full name is require field") String fullName) {
}
