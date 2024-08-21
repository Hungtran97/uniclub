package com.cybersoft.uniclub.controller;

import com.cybersoft.uniclub.request.UserCreationRequest;
import com.cybersoft.uniclub.response.BaseResponse;
import com.cybersoft.uniclub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/get")
    public ResponseEntity<?> getAllUsers(){
        BaseResponse response = new BaseResponse();
        response.setData(userService.getAllUser());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreationRequest request) {
        boolean isSuccess = userService.createUser(request);
        BaseResponse response = new BaseResponse();
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
