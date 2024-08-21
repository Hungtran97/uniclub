package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.dto.RoleDTO;
import com.cybersoft.uniclub.entity.RoleEntity;
import com.cybersoft.uniclub.request.AuthenRequest;
import com.cybersoft.uniclub.request.IntrospectRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;
import java.util.List;

public interface AuthenService {
    List<RoleDTO> checkLogin(AuthenRequest authenRequest);
}
