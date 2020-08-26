package com.landvibe.webidecontroller.project;

import com.landvibe.webidecontroller.project.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    private String projectId;
    private Project expected1;
    private Project expected2;

    @BeforeEach
    public void init() {
        projectId = "0";

        expected1 = Project.builder()
                .id("0")
                .name("test project1")
                .description("study")
                .status("running")
                .type("nodejs:12")
                .build();

        expected2 = Project.builder()
                .id("1")
                .name("test project2")
                .description("study")
                .status("stop")
                .type("nodejs:12")
                .build();
    }

    @Test
    public void test_findAll() {
        List<Project> expected = new ArrayList<>();
        expected.add(projectRepository.save(expected1));
        expected.add(projectRepository.save(expected2));

        List<Project> actual = projectRepository.findAll();

        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void test_findById() {
        List<Project> expected = new ArrayList<>();
        expected.add(projectRepository.save(expected1));
        expected.add(projectRepository.save(expected2));

        Optional<Project> actual = projectRepository.findById(projectId);

        assertThat(actual.isPresent()).isFalse();
        assertThat(actual.get().equals(expected1));
    }
}