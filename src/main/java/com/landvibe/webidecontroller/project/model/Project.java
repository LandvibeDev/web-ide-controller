package com.landvibe.webidecontroller.project.model;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Project")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    private String id;
    private String name;
    private String description;
    private String status;
    private String type;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime accessedAt;

    @CreatedBy
    private User user;

    @Builder
    public Project(String id, String name, String description, String status, String type,
                   LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime accessedAt, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.accessedAt = accessedAt;
        this.user = user;
    }
}