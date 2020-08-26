package com.landvibe.webidecontroller.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landvibe.webidecontroller.project.model.Project;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ProjectService.class)
public class ProjectServiceTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    private static MockWebServer mockWebServer;
    private String projectId;
    private Project expected;

    @BeforeAll
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public void init() {
        projectId = "0";

        expected = Project.builder()
                .id(projectId)
                .name("test project")
                .description("study")
                .type("nodejs:12")
                .build();
    }

    @Test
    public void test_listProjects() {
        List<Project> expectedList = new ArrayList<>();
        expectedList.add(expected);

        given(projectRepository.findAll()).willReturn(expectedList);

        final List<Project> actual = projectService.listProjects(0,1);

        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual).isEqualTo(expectedList);

        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void test_getProject() {
        given(projectRepository.findById(projectId)).willReturn(Optional.of(expected));

        final Project actual = projectService.getProject(projectId);

        assertThat(actual).isEqualTo(expected);

        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    public void test_createProject() {
        given(projectRepository.save(expected)).willReturn(expected);

        final Project actual = projectService.createProject("test project", "study", "nodejs:12");

        assertThat(actual).isEqualTo(expected);

        verify(projectRepository, times(1)).save(expected);
    }

    @Test
    public void test_updateProject() {
        final Project old = Project.builder()
                .id(projectId)
                .name("old")
                .description("old")
                .type("nodejs:12")
                .build();

        given(projectRepository.findById(projectId)).willReturn(Optional.of(old));
        given(projectRepository.save(expected)).willReturn(expected);

        final Project actual = projectService.updateProject(projectId, "test project", "study", "nodejs:12");

        assertThat(actual).isEqualTo(expected);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository,times(1)).save(expected);
    }

    @Test
    public void test_deleteProject() {
        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    public void test_runProject() throws Exception {
        final String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        final WebClient webClient = WebClient.create(baseUrl);

        projectService = new ProjectService(projectRepository, webClient);

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(expected))
                .addHeader("Content-Type", "application/json; charset-utf-8"));

        final Mono<Project> expectedMono = projectService.runProject(projectId);

        StepVerifier.create(expectedMono)
                .expectNextMatches(project -> project.getName().equals("test project"))
                .verifyComplete();

        verify(projectRepository, times(1)).findById(projectId);
    }
}