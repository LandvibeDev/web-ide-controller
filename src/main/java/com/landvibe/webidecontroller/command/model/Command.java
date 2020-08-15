package com.landvibe.webidecontroller.command.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "commands")
public class Command {
    @Id
    private String id;
    private String name;
    private String command;
    private String projectId;
}
