package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.dto.UserDTO;
import com.cybersoft.uniclub.request.UserCreationRequest;

import java.util.List;

public interface UserService {
    boolean createUser(UserCreationRequest request);
    List<UserDTO> getAllUser();
}
