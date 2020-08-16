package com.landvibe.webidecontroller.command.repository;

import com.landvibe.webidecontroller.command.model.Command;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class CommandRepositoryTest {

    @Autowired
    private CommandRepository commandRepository;

    @Test
    public void test_findAllByProjectId() {
        // given
        final String projectId = "testProjectId";
        final Command expected1 = Command.builder()
                .name("testName1")
                .command("testCommand1")
                .projectId(projectId)
                .build();
        final Command expected2 = Command.builder()
                .name("testName2")
                .command("testCommand2")
                .projectId(projectId)
                .build();
        final Command expected3 = Command.builder()
                .name("testName3")
                .command("testCommand3")
                .projectId(projectId)
                .build();

        List<Command> expected = new ArrayList<>();
        expected.add(commandRepository.save(expected1));
        expected.add(commandRepository.save(expected2));
        expected.add(commandRepository.save(expected3));

        // when
        List<Command> actual = commandRepository.findAllByProjectId(projectId);

        // then
        assertThat(actual.isEmpty()).isFalse();
        assertThat(actual).isEqualTo(expected);
    }
}