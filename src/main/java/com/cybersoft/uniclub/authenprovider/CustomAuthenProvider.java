package com.cybersoft.uniclub.authenprovider;

import com.cybersoft.uniclub.dto.RoleDTO;
import com.cybersoft.uniclub.exception.AuthenException;
import com.cybersoft.uniclub.request.AuthenRequest;
import com.cybersoft.uniclub.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
    private AuthenService authenService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        AuthenRequest request = new AuthenRequest(userName, password);

        List<RoleDTO> roles = authenService.checkLogin(request);
        if (roles.size()>0) {
            //StreamAPI
            // map() duyet qua mang va cho phep bien doi kieu du lieu tu du lieu goc
//            List<GrantedAuthority> authorityList = new ArrayList<>();
//            roles.forEach(roleDTO -> {
//                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleDTO.getName());
//                authorityList.add(simpleGrantedAuthority);
//
//            });
            List<SimpleGrantedAuthority> authorityList = roles.stream()
                    .map(item -> new SimpleGrantedAuthority(item.getName()))
                    .toList();

            return new UsernamePasswordAuthenticationToken("", "", authorityList);
        } else throw new AuthenException("Authen error");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
