package com.landvibe.webidecontroller.file.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "File")
@Getter
@Setter
@NoArgsConstructor
public class File {

    @Id
    private String id;
    private String name;
    private String type;
    private Integer permission;
    private String contents;
    private String parentId;

    @Builder
    public File(String id, String name, String type, Integer permission, String contents, String parentId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.permission = permission;
        this.contents = contents;
        this.parentId = parentId;
    }
}
