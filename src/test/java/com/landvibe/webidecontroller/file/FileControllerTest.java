package com.landvibe.webidecontroller.file;

import com.landvibe.webidecontroller.file.model.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileController.class)
@AutoConfigureMockMvc
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  FileService fileService;

    private String projectId;
    private String fileId;
    private File expected;

    @BeforeEach
    public void init() {
        projectId = "0";
        fileId = "1";

        expected = File.builder()
                .id(fileId)
                .name("src")
                .type("directory")
                .permission(777)
                .contents("test contents")
                .parentId("0")
                .build();
    }

    @Test
    public void test_getFiles() throws Exception {
        List<File> expectedList = new ArrayList<>();
        expectedList.add(expected);

        given(fileService.getFiles(projectId)).willReturn(expectedList);

        final ResultActions actions = mockMvc.perform(get("/projects/{pid}/files", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(fileId));

        verify(fileService, times(1)).getFiles(projectId);
    }

    @Test
    public void test_getFile() throws Exception {
        given(fileService.getFile(projectId, fileId)).willReturn(expected);

        final ResultActions actions = mockMvc.perform(get("/projects/{pid}/files/{fid}", projectId, fileId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(fileId))
                .andExpect(jsonPath("name").value("src"))
                .andExpect(jsonPath("type").value("directory"))
                .andExpect(jsonPath("permission").value(777))
                .andExpect(jsonPath("contents").value("test contents"))
                .andExpect(jsonPath("parentId").value("0"));

        verify(fileService, times(1)).getFile(projectId, fileId);
    }

    @Test
    public void test_createFile() throws Exception {
        given(fileService.createFile(projectId, "src", "directory", 777, "test contents", "0")).willReturn(expected);

        final ResultActions actions = mockMvc.perform(post("/projects/{pid}/files", projectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("src"))
                .andExpect(jsonPath("type").value("directory"))
                .andExpect(jsonPath("permission").value(777))
                .andExpect(jsonPath("contents").value("test contents"))
                .andExpect(jsonPath("parentId").value("0"));

        verify(fileService, times(1)).createFile(projectId, "src", "directory", 777, "test contents", "0");
    }

    @Test
    public void test_updateFile() throws Exception {
        given(fileService.updateFile(projectId, fileId, "src", "directory", 777, "test contents", "0")).willReturn(expected);

        final ResultActions actions = mockMvc.perform(put("/projects/{pid}/files/{fid}", projectId, fileId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(fileId))
                .andExpect(jsonPath("name").value("src"))
                .andExpect(jsonPath("type").value("directory"))
                .andExpect(jsonPath("permission").value(777))
                .andExpect(jsonPath("contents").value("test contents"))
                .andExpect(jsonPath("parentId").value("0"));

        verify(fileService, times(1)).updateFile(projectId, fileId, "src", "directory", 777, "test contents", "0");
    }

    @Test
    public void test_deleteFile() throws Exception {
        final ResultActions actions = mockMvc.perform(delete("/project/{pid}/files/{fid}", projectId, fileId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        actions.andExpect(status().isNoContent());

        verify(fileService, times(1)).deleteFile(projectId, fileId);
    }
}
