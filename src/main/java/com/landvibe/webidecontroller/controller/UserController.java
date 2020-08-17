package com.landvibe.webidecontroller.controller;

import com.landvibe.webidecontroller.dto.UserResponseDto;
import com.landvibe.webidecontroller.security.CurrentUser;
import com.landvibe.webidecontroller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.landvibe.webidecontroller.security.UserPrincipal;

@RestController
@RequiredArgsConstructor
public class UserController {
    // service
    private final UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    // @Cacheable(value = "users", key = "#userPrincipal.id")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        return userService.getCurrentUser(userPrincipal);
    }
}
