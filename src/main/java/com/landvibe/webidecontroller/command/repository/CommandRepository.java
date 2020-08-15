package com.landvibe.webidecontroller.command.repository;

import com.landvibe.webidecontroller.command.model.Command;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommandRepository extends MongoRepository<Command, String> {

    List<Command> findAllByProjectId(String projectId);
}
