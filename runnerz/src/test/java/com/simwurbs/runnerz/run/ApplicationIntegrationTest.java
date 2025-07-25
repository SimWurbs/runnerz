package com.simwurbs.runnerz.run;

import com.simwurbs.runnerz.run.Location;
import com.simwurbs.runnerz.run.Run;
import com.simwurbs.runnerz.run.RunRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ApplicationIntegrationTest {

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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RunRepository runRepository;

    @Test
    void shouldLoadApplicationContext() {
        // Test dass die Anwendung startet
        assertThat(runRepository).isNotNull();
    }

    @Test
    void shouldPerformCrudOperations() {
        String baseUrl = "http://localhost:" + port + "/api/runs";

        // Create
        Run newRun = new Run(1, "Integration Test Run", LocalDateTime.now(),
                            LocalDateTime.now().plusHours(1), 7, Location.OUTDOOR, 0);

        ResponseEntity<Void> createResponse = restTemplate.postForEntity(baseUrl, newRun, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Read All
        ResponseEntity<Run[]> getAllResponse = restTemplate.getForEntity(baseUrl, Run[].class);
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAllResponse.getBody()).isNotEmpty();
    }
}
