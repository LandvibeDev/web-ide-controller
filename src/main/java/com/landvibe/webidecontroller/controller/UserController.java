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

    /**
     * [ 2020-03-11 : 이민호 ]
     * 설명 : 본인의 유저 정보를 가져온다. (아이디, 이름, 이메일, 썸네일)
     * */
    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    // @Cacheable(value = "users", key = "#userPrincipal.id")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {

        return userService.getCurrentUser(userPrincipal);
    }

    /**
     * [ 2020-03-11 : 이민호 ]
     * 설명 : 유저의 정보를 업데이트 한다. (이름, 이미지)
     * */
}
