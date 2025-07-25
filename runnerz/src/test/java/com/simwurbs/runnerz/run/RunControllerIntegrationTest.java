package com.simwurbs.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@Testcontainers
@Transactional
@AutoConfigureMockMvc
class RunControllerIntegrationTest {

    @BeforeEach
    void setUp() {
        runRepository.deleteAll();
    }
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RunRepository runRepository;

    @Test
    void shouldGetAllRuns() throws Exception {
        // Given
        Run run = new Run(1, "Test Run", LocalDateTime.now(), 
                         LocalDateTime.now().plusHours(1), 5, Location.OUTDOOR, null);
        runRepository.save(run);

        // When & Then
        mockMvc.perform(get("/api/runs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Test Run"));
    }

    @Test
    void shouldCreateNewRun() throws Exception {
        // Given
        Run newRun = new Run(null, "New Run", LocalDateTime.now(), 
                            LocalDateTime.now().plusHours(1), 3, Location.INDOOR, null);

        // When & Then
        mockMvc.perform(post("/api/runs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRun)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetRunById() throws Exception {
        // Given
        Run run = new Run(1, "Test Run", LocalDateTime.now(), 
                         LocalDateTime.now().plusHours(1), 5, Location.OUTDOOR, null);
        runRepository.save(run);

        // When & Then
        mockMvc.perform(get("/api/runs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Run"))
                .andExpect(jsonPath("$.miles").value(5));
    }

    @Test
    void shouldReturnNotFoundForNonExistentRun() throws Exception {
        mockMvc.perform(get("/api/runs/999"))
                .andExpect(status().isNotFound());
    }
}
