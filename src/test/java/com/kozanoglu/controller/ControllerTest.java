package com.kozanoglu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kozanoglu.dto.User;
import com.kozanoglu.dto.UserPostResultDTO;
import com.kozanoglu.service.UserPostService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class ControllerTest {


    private MockMvc mockMvc;
    private UserPostService service;

    @Before
    public void setup() {
        service = Mockito.mock(UserPostService.class);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Controller(service)).build();
    }

    @Test
    public void shouldGet200WhenSuccessfulGet() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        this.mockMvc.perform(get("/api/userposts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserPostResultDTO(new User(), new ArrayList<>())))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}