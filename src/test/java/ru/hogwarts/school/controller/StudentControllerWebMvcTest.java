package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;
import ru.hogwarts.school.service.StudentServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @MockBean
    StudentsRepository studentsRepository;
    @SpyBean
    StudentServiceImpl studentService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    Student student = new Student(1L, "Harry Potter", 13);

    @Test
    void create_shouldReturnStudentAndGetStatusOk() throws Exception {
        when(studentsRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));

    }


    @Test
    void read_shouldThrowStudentNotFoundException() throws Exception {
        when(studentsRepository.findById(student.getId())).thenReturn(Optional.empty());

        mockMvc.perform(get("/student/" + student.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnStudentAndGetStatusOk() throws Exception {
        when(studentsRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentsRepository.save(student)).thenReturn(student);

        mockMvc.perform(put("/student")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(student));
    }


    @Test
    void delete_shouldThrowStudentNotFoundException() throws Exception {
        when(studentsRepository.findById((student.getId()))).thenReturn(Optional.empty());

        mockMvc.perform(delete("/student?age=" + student.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByAge_shouldReturnListOfStudentsAndStatusOk() throws Exception {
        Student student1 = new Student(2L, "Ron Wizlee", 13);
        when(studentsRepository.findByAge(student.getAge())).thenReturn(List.of(student, student1));

        mockMvc.perform(get("/student?age=" + student.getAge()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(student))
                .andExpect(jsonPath("$.[1]").value(student1));
    }

}

