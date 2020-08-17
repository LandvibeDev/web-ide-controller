package com.landvibe.webidecontroller.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserResponseDto implements Serializable {

    private String id;
    private String name;
    private String email;

    @Builder
    public UserResponseDto(String id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
