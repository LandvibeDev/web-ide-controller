package com.landvibe.webidecontroller.service;

import com.landvibe.webidecontroller.exception.ResourceNotFoundException;
import com.landvibe.webidecontroller.dto.UserResponseDto;
import com.landvibe.webidecontroller.repository.UserRepository;
import com.landvibe.webidecontroller.security.CurrentUser;
import com.landvibe.webidecontroller.model.User;
import com.landvibe.webidecontroller.security.UserPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Transactional()
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal){

        // user entity
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        // user response dto
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        // return response dto
        return userResponseDto;
    }
}
