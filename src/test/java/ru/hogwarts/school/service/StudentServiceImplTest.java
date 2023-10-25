package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    StudentService studentService = new StudentServiceImpl();
    Student potter = new Student(1, "Harry", 13);

    @Test
    void create_shouldCreateAndReturnStudent() {
        Student expectedStudent = new Student(1, "Harry", 13);
        ;
        assertEquals(expectedStudent, studentService.create(potter));
    }

    @Test
    void create_shouldThrowStudentAlreadyExistExceptionWhenStudentAreAlreadyInMap() {
        Student expectedStudent = new Student(1, "Harry", 13);
        studentService.create(expectedStudent);
        assertThrows(StudentAlreadyExistException.class, () -> studentService.create(potter));
    }

    @Test
    void read_shouldReadAndReturnStudent() {
        studentService.create(potter);
        assertEquals(potter, studentService.read(1L));
    }

    @Test
    void read_shouldThrowStudentNotFoundException() {
        assertThrows(StudentNotFoundException.class, () -> studentService.read(1L));
    }

    @Test
    void update_shouldReturnUpdateStudentWhenStudentAreInMap() {
        studentService.create(potter);
        Student expectedStudent = new Student(1, "Harry", 11);
        assertEquals(expectedStudent, studentService.update(expectedStudent));
    }

    @Test
    void update_shouldThrowStudentNotFoundExceptionWhenStudentAreNotInMap() {
        assertThrows(StudentNotFoundException.class, () -> studentService.update(potter));
    }

    @Test
    void delete_shouldThrowStudentNotFoundExceptionWhenStudentAreNotInMap() {
        assertThrows(StudentNotFoundException.class, () -> studentService.delete(1L));
    }

    @Test
    void delete_shouldDeleteAndReturnStudentWhenStudentAreInMap() {
        studentService.create(potter);
        assertEquals(potter, studentService.delete(1L));
    }

    @Test
    void readByAge_shouldReturnCollectionWhenStudentsWithThisAgeAreInMap() {
        Student graindger = new Student(1, "Germiona", 13);
        Student wizle = new Student(1, "Ron", 12);
        studentService.create(potter);
        studentService.create(graindger);
        studentService.create(wizle);
        assertEquals(2, studentService.readByAge(13).size());
    }
}