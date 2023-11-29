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
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    StudentsRepository studentsRepository;
    @LocalServerPort
    int port;
    Student student = new Student(1L, "Harry Potter", 13);
    String pathUrl;

    @BeforeEach
    void beforeEach() {
        pathUrl = "http://localhost:" + port + "/student";
        studentsRepository.deleteAll();
    }

    @Test
    void create_shouldReturnStudentAndStatusOk() {
        ResponseEntity<Student> result = testRestTemplate.postForEntity(pathUrl, student, Student.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(student, result.getBody());
    }

    @Test
    void read_shouldThrowStudentIsNotFound() {
        ResponseEntity<String> result = testRestTemplate.getForEntity(
                pathUrl + "/" + student.getId(), String.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void update_shouldReturnStudentAndStatusOk() {
        Student saveStudent = studentsRepository.save(student);

        ResponseEntity<Student> result = testRestTemplate.exchange(
                pathUrl, HttpMethod.PUT, new HttpEntity<>(saveStudent), Student.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(saveStudent, result.getBody());
    }

    @Test
    void delete_shouldThrowStudentIsNotFound() {
        ResponseEntity<Student> result = testRestTemplate.exchange(
                pathUrl + "/" + student.getId(), HttpMethod.DELETE, new HttpEntity<>(student), Student.class);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void readByAge_shouldReturnStudentsCollections() {
        Student saveStudent = studentsRepository.save(student);
        Student student1 = new Student(2L, "Ron WizLee", 13);
        Student saveStudent1 = studentsRepository.save(student1);

        ResponseEntity<List<Student>> result = testRestTemplate.exchange(
                pathUrl + "?age=" + student.getAge(), HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(List.of(saveStudent, saveStudent1), result.getBody());
    }
}
