package com.landvibe.webidecontroller.file;

import com.landvibe.webidecontroller.file.model.File;
import com.landvibe.webidecontroller.project.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = FileService.class)
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private FileRepository fileRepository;

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
    public void test_getFiles() {
        List<File> expectedList = new ArrayList<>();
        expectedList.add(expected);

        given(projectRepository.findById(projectId).get().getFiles()).willReturn(expectedList);

        final List<File> actual = fileService.getFiles(projectId);

        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual).isEqualTo(expectedList);

        verify(fileRepository, times(1)).findAll();
    }

    @Test
    public void test_getFile() {
        given(fileRepository.findById(fileId)).willReturn(Optional.of(expected));

        final File actual = fileService.getFile(projectId, fileId);

        assertThat(actual).isEqualTo(expected);

        verify(fileRepository, times(1)).findById(fileId);
    }

    @Test
    public void test_createFile() {
        given(fileRepository.insert(expected)).willReturn(expected);

        final File actual = fileService.createFile(projectId, "src", "directory", 777, "test contents", "0");

        assertThat(actual).isEqualTo(expected);

        verify(fileRepository, times(1)).insert(expected);
    }

    @Test
    public void test_updateFile() {
        given(fileRepository.save(expected)).willReturn(expected);

        final File actual = fileService.updateFile(projectId, fileId, "src", "directory", 777, "test contents", "0");

        assertThat(actual).isEqualTo(expected);

        verify(fileRepository, times(1)).save(expected);
    }

    @Test
    public void test_deleteFile() {
        fileService.deleteFile(projectId, fileId);

        verify(fileRepository, times(1)).deleteById(fileId);
    }
}
