package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @Mock
    FacultyRepository facultyRepository;
    @InjectMocks
    FacultyServiceImpl facultyService;

    Faculty faculty1 = new Faculty(1, "Griffindor", "Yellow");

    @Test
    void create_shouldCreateAndReturnFaculty() {
        Faculty faculty1 = new Faculty(1, "Griffindor", "Yellow");
        when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.of((faculty1)));
        assertEquals(faculty1,facultyService.create(faculty1));
    }
    @Test
    void create_shouldThrowFacultyAlreadyExistException() {
        when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.empty());
        assertThrows(FacultyAlreadyExistException.class,()->facultyService.create(faculty1));
    }


    @Test
    void read_shouldReadAndReturnFaculty() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty1));
        assertEquals(faculty1, facultyService.read(1L));
    }
    @Test
    void read_shouldThrowFacultyNotFoundException(){
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(FacultyNotFoundException.class,()->facultyService.read(1L));
    }

    @Test
    void update_shouldReturnUpdateFacultyWhenFacultyAreInRepository() {
        when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.of(faculty1));
        when(facultyRepository.save(faculty1)).thenReturn(faculty1);
        assertEquals(faculty1, facultyService.update(faculty1));
    }
    @Test
    void update_shouldThrowFacultyNotFoundException(){
        when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.empty());
        assertThrows(FacultyNotFoundException.class,()->facultyService.update(faculty1));
    }


    @Test
    void delete_shouldDeleteWhenFacultyAreInRepository() {
        when(facultyRepository.findById(faculty1.getId())).thenReturn(Optional.of(faculty1));
        assertEquals(faculty1, facultyService.delete(1L));

    }

    @Test
    void readByAge_shouldReturnCollectionWhenFacultiesWithThisColorAreInRepository() {
        Faculty faculty2 = new Faculty(1, "Slizerin", "Green");
        Faculty faculty3 = new Faculty(1, "Kogtevran", "Green");
        List<Faculty> facultyList= List.of(faculty2,faculty3);
        when(facultyRepository.findByColor("Green")).thenReturn(facultyList);
        assertEquals(2, facultyRepository.findByColor("Green").size());
    }
}