package ru.hogwarts.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    FacultyRepository facultyRepository;
    @LocalServerPort
    int port;
    Faculty faculty = new Faculty(1L, "Griffendor", "Green&White");
    String pathUrl;

    @BeforeEach
    void beforeEach() {
        pathUrl = "http://localhost:" + port + "/student";
        facultyRepository.deleteAll();
    }

    @Test
    void create_shouldReturnFacultyAndStatusOk() {
        ResponseEntity<Faculty> result = testRestTemplate.postForEntity(pathUrl, faculty, Faculty.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(faculty, result.getBody());
    }

    @Test
    void read_shouldThrowFacultyIsNotFound() {
        ResponseEntity<Faculty> result = testRestTemplate.getForEntity(
                pathUrl + "/" + faculty.getId(), Faculty.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void update_shouldReturnFacultyAndStatusOk() {
        Faculty saveFaculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> result = testRestTemplate.exchange(
                pathUrl, HttpMethod.PUT, new HttpEntity<>(saveFaculty), Faculty.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveFaculty, result.getBody());
    }

    @Test
    void delete_shouldThrowFacultyIsNotFound() {
        ResponseEntity<Faculty> result = testRestTemplate.exchange(
                pathUrl + "/" + faculty.getId(), HttpMethod.DELETE, new HttpEntity<>(faculty), Faculty.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void readByColor_shouldReturnFacultiesCollections() {
        Faculty saveFaculty = facultyRepository.save(faculty);
        Faculty faculty1 = new Faculty(2L, "Kogtewran", "Green&White");
        Faculty saveFaculty1 = facultyRepository.save(faculty1);
        ;

        ResponseEntity<List<Faculty>> result = testRestTemplate.exchange(
                pathUrl + "/" + faculty.getColor(), HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(saveFaculty, saveFaculty1), result.getBody());
    }

}
