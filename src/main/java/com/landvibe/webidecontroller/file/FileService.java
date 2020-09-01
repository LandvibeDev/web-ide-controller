package com.landvibe.webidecontroller.file;

import com.landvibe.webidecontroller.file.model.File;
import com.landvibe.webidecontroller.project.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private ProjectRepository projectRepository;
    private FileRepository fileRepository;

    public FileService(ProjectRepository projectRepository, FileRepository fileRepository) {
        this.projectRepository = projectRepository;
        this.fileRepository = fileRepository;
    }

    public List<File> getFiles(String pid) {
        List<File> findFiles = this.projectRepository.findById(pid).get().getFiles();

        return findFiles;
    }

    public File getFile(String pid, String fid) {
        return this.fileRepository.findById(fid).get();
    }

    public File createFile(String pid, String name, String type, Integer permission, String contents, String parentId) {
        File newFile = File.builder()
                .name(name)
                .type(type)
                .permission(permission)
                .contents(contents)
                .parentId(parentId)
                .build();
        
        return this.fileRepository.insert(newFile);
    }

    public File updateFile(String pid, String fid, String name, String type, Integer permission, String contents, String parentId) {
        File editFile = File.builder()
                .id(fid)
                .name(name)
                .type(type)
                .permission(permission)
                .contents(contents)
                .parentId(parentId)
                .build();

        return this.fileRepository.save(editFile);
    }

    public void deleteFile(String pid, String fid) {
        this.fileRepository.deleteById(fid);
    }
}
