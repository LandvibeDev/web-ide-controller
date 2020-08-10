package com.landvibe.webidecontroller.project.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Project {

    int id;
    String name;
    String description;
    String status;
    String type;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    LocalDateTime accessedAt;

    @Builder
    public Project(int id, String name, String description, String status, String type, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime accessedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accessedAt = accessedAt;
    }
}
