package com.landvibe.webidecontroller.command.service;

import com.landvibe.webidecontroller.command.model.Command;
import com.landvibe.webidecontroller.command.repository.CommandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandService {

    private CommandRepository commandRepository;

    public CommandService(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    public List<Command> getCommands(String projectId) {
        return commandRepository.findAllByProjectId(projectId);
    }

    public Optional<Command> getCommand(String commandId) {
        return commandRepository.findById(commandId);
    }

    public Command createCommand(Command command) {
        return commandRepository.save(command);
    }

    public Optional<Command> updateCommand(Command command) {
        return commandRepository.findById(command.getId())
                .map(oldCommand -> {
                    oldCommand.setName(command.getName());
                    oldCommand.setCommand(command.getCommand());
                    oldCommand.setProjectId(command.getProjectId());
                    return oldCommand;
                })
                .map(newCommand -> commandRepository.save(command));
    }

    public void deleteCommand(String id) {
        commandRepository.deleteById(id);
    }
}
