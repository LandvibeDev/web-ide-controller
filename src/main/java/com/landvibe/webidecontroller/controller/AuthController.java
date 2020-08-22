package com.landvibe.webidecontroller.controller;

import com.landvibe.webidecontroller.exception.BadRequestException;
import com.landvibe.webidecontroller.exception.ResourceNotFoundException;
import com.landvibe.webidecontroller.model.AuthProvider;
import com.landvibe.webidecontroller.model.User;
import com.landvibe.webidecontroller.dto.ApiResponse;
import com.landvibe.webidecontroller.dto.AuthResponse;
import com.landvibe.webidecontroller.dto.LoginRequest;
import com.landvibe.webidecontroller.dto.SignUpRequest;
import com.landvibe.webidecontroller.repository.UserRepository;
import com.landvibe.webidecontroller.security.TokenProvider;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

//    @PostMapping(value = "/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = tokenProvider.createToken(authentication);
//        return ResponseEntity.ok(new AuthResponse(token));
//    }

    @PostMapping("/signup")
    public String registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws NotFoundException {
        // Creating user's account
        User user = userRepository.findByEmailAndProvider(signUpRequest.getEmail(), signUpRequest.getProvider())
                .orElseThrow(() -> new NotFoundException("not found"));

        if(user.getRegistered()) {
            // error
        }

        user.setNickname(signUpRequest.getNickname());
        user.setIntroduce(signUpRequest.getIntroduce());
        user.setRegistered(true);
        userRepository.save(user);
        String token = tokenProvider.createToken(user);

        return token;
    }
}
