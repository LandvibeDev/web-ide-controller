package com.landvibe.webidecontroller.service;

import com.landvibe.webidecontroller.dto.UserResponseDto;
import com.landvibe.webidecontroller.security.CurrentUser;
import com.landvibe.webidecontroller.security.UserPrincipal;


public interface UserService {
    UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal);
}
