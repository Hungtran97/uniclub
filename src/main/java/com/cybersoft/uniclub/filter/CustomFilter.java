package com.cybersoft.uniclub.filter;

import com.cybersoft.uniclub.dto.AuthorityDTO;
import com.cybersoft.uniclub.exception.AuthenException;
import com.cybersoft.uniclub.request.IntrospectRequest;
import com.cybersoft.uniclub.ultis.JwtHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String authorHeader = request.getHeader("Authorization");
        if (authorHeader != null && authorHeader.startsWith("Bearer ")) {
            String token = authorHeader.substring(7);
            IntrospectRequest introspectRequest = new IntrospectRequest();
            introspectRequest.setToken(token);
            String data =  jwtHelper.introspectToken2(introspectRequest);

            if (data != null){
                List<AuthorityDTO> authorityDTOList = null;
                try {
                    authorityDTOList = objectMapper.readValue(data, new TypeReference<List<AuthorityDTO>>() {});
                } catch (Exception e) {
                    throw new AuthenException(e.getMessage());
                }
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorityDTOList.forEach(dataDTO -> {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(dataDTO.getAuthority());
                    authorities.add(simpleGrantedAuthority);
                });

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("","", authorities);
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authenticationToken);
            }

        }
        try {
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            throw new AuthenException(e.getMessage());
        }
    }
}
