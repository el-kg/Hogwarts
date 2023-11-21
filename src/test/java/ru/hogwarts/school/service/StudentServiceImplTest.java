package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.*;

import static java.util.Optional.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    public class StudentServiceImplTest {
        @Mock
        StudentsRepository studentsRepository;
        @InjectMocks
        StudentServiceImpl studentService;

    Student potter = new Student(1L, "Harry", 13);


    @Test
    void create_shouldCreateAndReturnStudent() {
        Student expectedStudent = new Student(1L, "Harry", 13);
        when(studentsRepository.findById(1L)).thenReturn(of(expectedStudent));
        assertEquals(expectedStudent, studentService.create(potter));
    }
    @Test
    void create_shouldThrowStudentAlreadyAddedException() {
        when(studentsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentAlreadyExistException.class,()-> studentService.create(potter));
    }

    @Test
    void read_shouldReadAndReturnStudent() {
        Student expectedStudent = new Student(1L, "Harry", 13);
        when(studentsRepository.findById(1L)).thenReturn(of(expectedStudent));
        assertEquals(expectedStudent, studentService.read(1L));
    }
    @Test
    void read_shouldThrowStudentNotFoundException() {
        when(studentsRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class,()-> studentService.read(1L));
    }

    @Test
    void update_shouldReturnUpdateStudentWhenStudentAreInRepository() {
        when(studentsRepository.findById(potter.getId())).thenReturn(of(potter));
        assertEquals(potter, studentService.update(potter));
    }
    @Test
    void update_shouldThrowStudentNotFoundException() {
        when(studentsRepository.findById(potter.getId())).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class,()-> studentService.update(potter));
    }

    @Test
    void delete_shouldReturnDeleteWhenStudentAreInRepository() {
        when(studentsRepository.findById(potter.getId())).thenReturn(of(potter));
        assertEquals(of(potter),studentService.delete(1L));
    }

    @Test
    void readByAge_shouldReturnCollectionWhenStudentsWithThisAgeAreInRepository() {
        Student graindger = new Student(1, "Germiona", 13);
        Student wizle = new Student(1, "Ron", 12);
        List<Student> expectedList= List.of(potter,graindger);
        when(studentsRepository.findByAge(13)).thenReturn(expectedList);
        assertEquals(2, studentService.findByAge(13).size());
    }
    @Test
    void findByAgeBetween_shouldReturnCollectionWhenStudentsWithThisAgeAreInRepository(){
        Student graindger = new Student(1, "Germiona", 13);
        Student wizle = new Student(1, "Ron", 12);
        Collection<Student> expectedResult= List.of(potter,graindger);
        when(studentsRepository.findByAgeBetween(10,20)).thenReturn(expectedResult);
        assertEquals(expectedResult,studentService.findByAgeBetween(10,20));
    }
    @Test
    void readStudentFaculty_shouldReturnStudentFaculty(){
        Faculty grifindor= new Faculty(1L,"Гриффиндор","Зеленый");
        potter.setFaculty(grifindor);
        when(studentsRepository.findById(potter.getId())).thenReturn(of(potter));
        assertEquals(grifindor,studentService.readStudentFaculty(potter.getId()));
    }

    @Test
    void readByFacultyId_shouldReturnCollectionOfStudentsWithFaculty(){
        Student graindger = new Student(1, "Germiona", 13);
        Student wizle = new Student(1, "Ron", 12);
        Collection<Student> expectedResult= List.of(potter,graindger);
        when(studentsRepository.findAllByFaculty_id(1L)).thenReturn(expectedResult);
        assertEquals(expectedResult,studentService.readByFacultyId(1L));
    }
}