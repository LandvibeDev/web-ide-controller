package com.landvibe.webidecontroller.file;

import com.landvibe.webidecontroller.file.model.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    private String fileId;
    private File expected1;
    private File expected2;

    @BeforeEach
    public void init() {
        fileId = "1";

        expected1 = File.builder()
                .id("0")
                .name("src")
                .type("directory")
                .permission(777)
                .build();

        expected2 = File.builder()
                .id(fileId)
                .name("test.js")
                .type("file")
                .permission(777)
                .contents("test contents")
                .parentId("0")
                .build();
    }

    @Test
    public void test_findById() {
        List<File> expected = new ArrayList<>();
        expected.add(expected1);
        expected.add(expected2);

        Optional<File> actual = fileRepository.findById(fileId);

        assertThat(actual.isPresent()).isFalse();
        assertThat(actual.get()).isEqualTo(expected2);
    }
}
