package com.cybersoft.uniclub.service.imp;

import com.cybersoft.uniclub.dto.RoleDTO;
import com.cybersoft.uniclub.entity.RoleEntity;
import com.cybersoft.uniclub.entity.UserEntity;
import com.cybersoft.uniclub.repository.UserRepository;
import com.cybersoft.uniclub.request.AuthenRequest;
import com.cybersoft.uniclub.request.IntrospectRequest;
import com.cybersoft.uniclub.service.AuthenService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuthenServiceImp implements AuthenService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<RoleDTO> checkLogin(AuthenRequest authenRequest) {
        UserEntity user = userRepository.findUserEntityByEmail(authenRequest.email());
        List<RoleDTO> roleDTOs = new ArrayList<>();
        if (user != null && passwordEncoder.matches(authenRequest.password(), user.getPassword())){
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(user.getRole().getId());
            roleDTO.setName(user.getRole().getName());
            roleDTOs.add(roleDTO);
        }
        return roleDTOs;
    }


}
