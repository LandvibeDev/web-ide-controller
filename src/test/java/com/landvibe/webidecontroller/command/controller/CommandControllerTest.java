package com.landvibe.webidecontroller.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.landvibe.webidecontroller.command.model.Command;
import com.landvibe.webidecontroller.command.service.CommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CommandControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CommandService commandService;

    @InjectMocks
    private CommandController commandController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commandController).build();
    }

    @Test
    public void test_get_command() throws Exception {
        // given
        final String commandId = "testId";
        final Command expected = Command.builder()
                .id(commandId)
                .name("testName")
                .command("testCommand")
                .projectId("testProjectId")
                .build();

        given(commandService.getCommand(commandId)).willReturn(Optional.of(expected));

        // when, then
        mockMvc.perform(get("/commands/" + commandId))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(commandService, times(1)).getCommand(commandId);
    }

    @Test
    public void test_get_commands() throws Exception {
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

        given(commandService.getCommands(projectId)).willReturn(expected);

        mockMvc.perform(get("/commands").queryParam("projectId", projectId))
                .andExpect(content().string(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(commandService, times(1)).getCommands(projectId);
    }

    @Test
    public void test_create_command() throws Exception {
        // given
        final Command expected = Command.builder()
                .name("testName")
                .command("testCommand")
                .projectId("testProjectId")
                .build();

        final String body = objectMapper.writeValueAsString(expected);

        given(commandService.createCommand(expected)).willReturn(expected);

        // when, then
        mockMvc.perform(
                post("/commands")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(body))
                .andExpect(status().isCreated())
                .andDo(print());

        verify(commandService, times(1)).createCommand(expected);
    }

    @Test
    public void test_update_command() throws Exception {
        // given
        final String commandId = "testId";
        final Command expected = Command.builder()
                .id(commandId)
                .name("testName")
                .command("testCommand")
                .projectId("testProjectId")
                .build();

        final String body = objectMapper.writeValueAsString(expected);

        given(commandService.updateCommand(expected)).willReturn(Optional.of(expected));

        // when, then
        mockMvc.perform(
                put("/commands/" + commandId)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(body))
                .andExpect(status().isOk())
                .andDo(print());

        verify(commandService, times(1)).updateCommand(expected);
    }

    @Test
    public void test_delete_command() throws Exception {
        // given
        final String commandId = "testId";

        // when, then
        mockMvc.perform(delete("/commands/" + commandId))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(commandService, times(1)).deleteCommand(commandId);
    }
}