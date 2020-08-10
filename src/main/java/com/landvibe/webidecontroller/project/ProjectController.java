package com.landvibe.webidecontroller.project;

import com.landvibe.webidecontroller.project.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Project> listProjects(@RequestParam int skip, @RequestParam int limit) {
        Project projectEx = Project.builder()
                .id(1)
                .name("sample project")
                .status("running")
                .type("nodejs:12")
                .build();

        List<Project> listProjectEx = new ArrayList<>();
        listProjectEx.add(projectEx);

        return listProjectEx;
    }
}
