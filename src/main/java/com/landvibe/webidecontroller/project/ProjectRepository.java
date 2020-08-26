package com.landvibe.webidecontroller.project;

import com.landvibe.webidecontroller.project.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
}