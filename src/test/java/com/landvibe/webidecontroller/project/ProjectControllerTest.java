package com.landvibe.webidecontroller.project;

import com.landvibe.webidecontroller.project.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectController.class)
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    private String projectId;
    private Project expected;

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
    public void test_listProjects() throws Exception {
        List<Project> expectedList = new ArrayList<>();
        expectedList.add(expected);

        given(projectService.listProjects(0,1)).willReturn(expectedList);

        final ResultActions actions = mockMvc.perform(get("/projects?skip=0&limit=1"))
                .andExpect(status().isOk())
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(projectId));

        verify(projectService, times(1)).listProjects(0,1);
    }

    @Test
    public void test_getProject() throws Exception {
        given(projectService.getProject(projectId)).willReturn(expected);

        final ResultActions actions = mockMvc.perform(get("/projects/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(projectId))
                .andExpect(jsonPath("name").value("test project"))
                .andExpect(jsonPath("description").value("study"))
                .andExpect(jsonPath("type").value("nodejs:12"));

        verify(projectService, times(1)).getProject(projectId);
    }

    @Test
    public void test_createProject() throws Exception {
        given(projectService.createProject("test project", "study", "nodejs:12")).willReturn(expected);

        final ResultActions actions = mockMvc.perform(post("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("test project"))
                .andExpect(jsonPath("description").value("study"))
                .andExpect(jsonPath("type").value("nodejs:12"));

        verify(projectService, times(1)).createProject("test project", "study", "nodejs:12");
    }

    @Test
    public void test_updateProject() throws Exception {
        given(projectService.updateProject(projectId, "test project", "study", "nodejs:12")).willReturn(expected);

        final ResultActions actions = mockMvc.perform(put("/projects/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("test project"))
                .andExpect(jsonPath("description").value("study"))
                .andExpect(jsonPath("type").value("nodejs:12"));

        verify(projectService, times(1)).updateProject(projectId, "test project", "stduy", "nodejs:12");
    }

    @Test
    public void test_deleteProject() throws Exception {
        final ResultActions actions = mockMvc.perform(delete("/projects/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions.andExpect(status().isNoContent());

        verify(projectService, times(1)).deleteProject(projectId);
    }

    @Test
    public void test_runProject() throws Exception {
        Mono<Project> expectedMono = Mono.just(expected);

        given(projectService.runProject(projectId)).willReturn(expectedMono);

        final ResultActions actions = mockMvc.perform(post("/projects/{id}", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(projectId))
                .andExpect(jsonPath("name").value("test project"))
                .andExpect(jsonPath("description").value("study"))
                .andExpect(jsonPath("type").value("nodejs:12"));

        verify(projectService, times(1)).runProject(projectId);
    }
}