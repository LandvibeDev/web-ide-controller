package com.landvibe.webidecontroller.file;

import com.landvibe.webidecontroller.file.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {
}
