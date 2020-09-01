package com.landvibe.webidecontroller.file;

import com.landvibe.webidecontroller.file.model.File;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{pid}/files")
    @ResponseStatus(HttpStatus.OK)
    public List<File> getFiles(@PathVariable String pid) {
        return this.fileService.getFiles(pid);
    }

    @GetMapping("/{pid}/files/{fid}")
    @ResponseStatus(HttpStatus.OK)
    public File getFile(@PathVariable String pid, @PathVariable String fid) {
        return this.fileService.getFile(pid, fid);
    }

    @PostMapping("/{pid}/files")
    @ResponseStatus(HttpStatus.CREATED)
    public File createFile(@PathVariable String pid,
                           @RequestParam String name,
                           @RequestParam String type,
                           @RequestParam Integer permission,
                           @RequestParam String contents,
                           @RequestParam String parentId) {
        return this.fileService.createFile(pid, name, type, permission, contents, parentId);
    }

    @PutMapping("/{pid}/files/{fid}")
    @ResponseStatus(HttpStatus.OK)
    public File updateFile(@PathVariable String pid,
                           @PathVariable String fid,
                           @RequestParam String name,
                           @RequestParam String type,
                           @RequestParam Integer permission,
                           @RequestParam String contents,
                           @RequestParam String parentId) {
        return this.fileService.updateFile(pid, fid, name, type, permission, contents, parentId);
    }

    @DeleteMapping("/{pid}/files/{fid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFile(@PathVariable String pid, @PathVariable String fid) {
        this.fileService.deleteFile(pid, fid);
    }
}
