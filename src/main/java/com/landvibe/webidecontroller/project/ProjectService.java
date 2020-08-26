package com.landvibe.webidecontroller.project;

import com.landvibe.webidecontroller.project.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProjectService {

    @Value("${workerpool.endpoints}")
    private String workerpoolEndpoints;

    private WebClient webClient;
    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository, WebClient webClient) {
        this.projectRepository = projectRepository;
        this.webClient = webClient;
    }

    public List<Project> listProjects(int skip, int limit) {
        return this.projectRepository.findAll(PageRequest.of(skip, limit)).getContent();
    }

    public Project getProject(String id) throws RuntimeException {
        if (!this.projectRepository.findById(id).isPresent()) {
            throw new RuntimeException("This id is not present.");
        }

        return this.projectRepository.findById(id).get();
    }

    public Project createProject(String name, String description, String type) {
        Project newProject = Project.builder()
                .name(name)
                .description(description)
                .type(type)
                .build();

        return this.projectRepository.insert(newProject);
    }

    public Project updateProject(String id, String name, String description, String type) {
        Project editProject = Project.builder()
                .id(id)
                .name(name)
                .description(description)
                .type(type)
                .build();

        return this.projectRepository.save(editProject);
    }

    public void deleteProject(String id) {
        this.projectRepository.deleteById(id);
    }

    public Mono<Project> runProject(String id) {
        return webClient.mutate()
                .baseUrl("http://" + workerpoolEndpoints)
                .build()
                .get()
                .uri("/projects/{id}")
                .retrieve()
                .bodyToMono(Project.class);
    }
}