package com.landvibe.webidecontroller.project;

import com.landvibe.webidecontroller.project.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> listProjects(@RequestParam int skip, @RequestParam int limit) {
        return this.projectService.listProjects(skip, limit);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Project getProject(@PathVariable String id) throws RuntimeException {
        return this.projectService.getProject(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Project createProject(@RequestParam String name, @RequestParam String description, @RequestParam String type) {
        return this.projectService.createProject(name, description, type);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Project updateProject(@PathVariable String id, @RequestParam String name, @RequestParam String description, @RequestParam String type) {
        return this.projectService.updateProject(id, name, description, type);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable String id) {
        this.projectService.deleteProject(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Project> runProject(@PathVariable String id) {
        return this.projectService.runProject(id);
    }
}