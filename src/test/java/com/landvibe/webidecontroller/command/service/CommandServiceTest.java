package com.landvibe.webidecontroller.command.service;

import com.landvibe.webidecontroller.command.model.Command;
import com.landvibe.webidecontroller.command.repository.CommandRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommandServiceTest {

    @InjectMocks
    private CommandService commandService;

    @Mock
    private CommandRepository commandRepository;

    @Test
    public void test_get_commands() {
        // given
        final String commandId = "testId";
        final String projectId = "testProjectId";
        final Command expected1 = Command.builder()
                .id(commandId)
                .name("testName1")
                .command("testCommand1")
                .projectId(projectId)
                .build();
        final Command expected2 = Command.builder()
                .id(commandId)
                .name("testName2")
                .command("testCommand2")
                .projectId(projectId)
                .build();
        final Command expected3 = Command.builder()
                .id(commandId)
                .name("testName3")
                .command("testCommand3")
                .projectId(projectId)
                .build();

        List<Command> expected = new ArrayList<>();
        expected.add(expected1);
        expected.add(expected2);
        expected.add(expected3);

        given(commandRepository.findAllByProjectId(projectId)).willReturn(expected);

        // when
        final List<Command> actual = commandService.getCommands(projectId);

        // then
        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual).isEqualTo(expected);

        verify(commandRepository, times(1)).findAllByProjectId(projectId);
    }

    @Test
    public void test_get_command() {
        // given
        final String commandId = "testId";
        final Command expected = Command.builder()
                .id(commandId)
                .name("testName")
                .command("testCommand")
                .projectId("testProjectId")
                .build();

        given(commandRepository.findById(commandId)).willReturn(Optional.of(expected));

        // when
        final Optional<Command> actual = commandService.getCommand(commandId);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);

        verify(commandRepository, times(1)).findById(commandId);
    }

    @Test
    public void test_create_command() {
        // given
        final String commandId = "testId";
        final Command expected = Command.builder()
                .id(commandId)
                .name("testName")
                .command("testCommand")
                .projectId("testProjectId")
                .build();

        given(commandRepository.save(expected)).willReturn(expected);

        // when
        final Command actual = commandService.createCommand(expected);

        // then
        assertThat(actual).isEqualTo(expected);

        verify(commandRepository, times(1)).save(expected);
    }

    @Test
    public void test_update_command() {
        // given
        final String commandId = "testId";
        final Command old = Command.builder()
                .id(commandId)
                .name("old")
                .command("old")
                .projectId("old")
                .build();

        final Command expected = Command.builder()
                .id(commandId)
                .name("testName")
                .command("testCommand")
                .projectId("testProjectId")
                .build();

        given(commandRepository.findById(commandId)).willReturn(Optional.of(old));
        given(commandRepository.save(expected)).willReturn(expected);

        // when
        final Optional<Command> actual = commandService.updateCommand(expected);

        // then
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get()).isEqualTo(expected);

        verify(commandRepository, times(1)).findById(commandId);
        verify(commandRepository, times(1)).save(expected);
    }

    @Test
    public void test_delete_command() {
        // given
        final String commandId = "testId";

        // when
        commandService.deleteCommand(commandId);

        // then
        verify(commandRepository, times(1)).deleteById(commandId);
    }
}