package com.simwurbs.runnerz.run;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RunRepositoryTest {

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
    private RunRepository runRepository;

    @Test
    void shouldSaveAndFindRun() {
        // Given
        Run run = new Run(
                1,
                "Test Run",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                5,
                Location.OUTDOOR,
                null
        );

        // When
        runRepository.save(run);
        Optional<Run> found = runRepository.findById(1);

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().title()).isEqualTo("Test Run");
        assertThat(found.get().miles()).isEqualTo(5);
    }

    @Test
    void shouldFindRunsByLocation() {
        // Given
        Run outdoorRun = new Run(1, "Outdoor Run", LocalDateTime.now(), 
                                LocalDateTime.now().plusHours(1), 5, Location.OUTDOOR, null);
        Run indoorRun = new Run(2, "Indoor Run", LocalDateTime.now(), 
                               LocalDateTime.now().plusHours(1), 3, Location.INDOOR, null);
        
        runRepository.saveAll(List.of(outdoorRun, indoorRun));

        // When
        List<Run> outdoorRuns = runRepository.findAllByLocation("OUTDOOR");

        // Then
        assertThat(outdoorRuns).hasSize(1);
        assertThat(outdoorRuns.get(0).title()).isEqualTo("Outdoor Run");
    }
}