package com.landvibe.webidecontroller.command.controller;

import com.landvibe.webidecontroller.command.model.Command;
import com.landvibe.webidecontroller.command.service.CommandService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commands")
public class CommandController {

    private CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping(value = "/{commandId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Command> getCommand(@PathVariable String commandId) {
        return commandService.getCommand(commandId);
    }

    @GetMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<Command> getCommands(@RequestParam String projectId) {
        return commandService.getCommands(projectId);
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Command createCommand(@RequestBody Command command) {
        return commandService.createCommand(command);
    }

    @PutMapping(value = "/{commandId}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Command> updateCommand(@PathVariable String commandId, @RequestBody Command command) {
        command.setId(commandId);
        return commandService.updateCommand(command);
    }

    @DeleteMapping(value = "/{commandId}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommand(@PathVariable String commandId) {
        commandService.deleteCommand(commandId);
    }
}
