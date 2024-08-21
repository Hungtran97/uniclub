package com.cybersoft.uniclub.controller;

import com.cybersoft.uniclub.request.AuthenRequest;
import com.cybersoft.uniclub.response.BaseResponse;
import com.cybersoft.uniclub.service.AuthenService;
import com.cybersoft.uniclub.ultis.JwtHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authen")
@CrossOrigin
public class AuthenController {
    @Autowired
    private AuthenService authenService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    AuthenticationManager authenticationManager;
    ObjectMapper objectMap = new ObjectMapper();
    @PostMapping
    public ResponseEntity<?> Authen (@RequestBody @Valid AuthenRequest authenRequest) throws JsonProcessingException {
        //boolean isSuccess = authenService.checkLogin(authenRequest);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenRequest.email(), authenRequest.password());
        Authentication authentication= authenticationManager.authenticate(authenticationToken);
        BaseResponse response = new BaseResponse();

        List<GrantedAuthority> listRole = (List<GrantedAuthority>) authentication.getAuthorities();
        String data = objectMap.writeValueAsString(listRole);
        String token = jwtHelper.generateToken2(data);
        response.setData(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
