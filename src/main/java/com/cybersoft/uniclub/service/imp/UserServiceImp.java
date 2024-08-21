package com.cybersoft.uniclub.service.imp;

import com.cybersoft.uniclub.dto.UserDTO;
import com.cybersoft.uniclub.entity.UserEntity;
import com.cybersoft.uniclub.repository.UserRepository;
import com.cybersoft.uniclub.request.UserCreationRequest;
import com.cybersoft.uniclub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public boolean createUser(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.email()))
            throw new RuntimeException("User existed");
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setFullName(request.fullName());
        user.setPassword(passwordEncoder.encode(request.password()));
        boolean isSucces = false;
        if (userRepository.save(user).getId() != 0){
            isSucces = true;
        }
        return isSucces;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<UserDTO> userDTOS = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity data :
                userEntities) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(data.getEmail());
            userDTO.setFullName(data.getFullName());

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
