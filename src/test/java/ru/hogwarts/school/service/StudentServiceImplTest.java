package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
 public StudentServiceImplTest (StudentsRepository studentsRepository){this.studentsRepository = studentsRepository;}


    StudentsRepository studentsRepository;
    Student potter = new Student(1, "Harry", 13);

    @Test
    void create_shouldCreateAndReturnStudent() {
        Student expectedStudent = new Student(1, "Harry", 13);
                assertEquals(expectedStudent, studentsRepository.save(potter));
    }
    @Test
    void create_shouldThrowStudentAlreadyAddedException() {
        studentsRepository.save(potter);
        assertThrows(StudentAlreadyExistException.class,()-> studentsRepository.save(potter));
    }


    @Test
    void read_shouldReadAndReturnStudent() {
        studentsRepository.save(potter);
        assertEquals(potter, studentsRepository.findById(1L).get());
    }
    @Test
    void read_shouldThrowStudentNotFoundException() {
        studentsRepository.save(potter);
        assertThrows(StudentNotFoundException.class,()-> studentsRepository.findById(1L).get());
    }

    @Test
    void update_shouldReturnUpdateStudentWhenStudentAreInRepository() {
        studentsRepository.save(potter);
        Student expectedStudent = new Student(1, "Harry", 11);
        assertEquals(expectedStudent, studentsRepository.save(expectedStudent));
    }


    @Test
    void delete_shouldDeleteWhenStudentAreInRepository() {
        studentsRepository.save(potter);
        studentsRepository.deleteById(1L);
    }

    @Test
    void readByAge_shouldReturnCollectionWhenStudentsWithThisAgeAreInMap() {
        Student graindger = new Student(1, "Germiona", 13);
        Student wizle = new Student(1, "Ron", 12);
        studentsRepository.save(potter);
        studentsRepository.save(graindger);
        studentsRepository.save(wizle);
        assertEquals(2, studentsRepository.findByAge(13).size());
    }
}