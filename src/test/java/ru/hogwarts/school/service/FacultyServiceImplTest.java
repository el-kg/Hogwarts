package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {
    FacultyRepository facultyRepository;
    public FacultyServiceImplTest(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    Faculty faculty1 = new Faculty(1, "Griffindor", "Yellow");

    @Test
    void create_shouldCreateAndReturnFaculty() {
        assertEquals(faculty1, facultyRepository.save(faculty1));
    }


    @Test
    void read_shouldReadAndReturnFaculty() {
        facultyRepository.save(faculty1);
        assertEquals(faculty1, facultyRepository.findById(1L).get());
    }

    @Test
    void update_shouldReturnUpdateFacultyWhenFacultyAreInRepository() {
        facultyRepository.save(faculty1);
        Faculty expectedFaculty = new Faculty(1, "Griffindor", "Yellow");
        assertEquals(expectedFaculty, facultyRepository.save(expectedFaculty));
    }


    @Test
    void delete_shouldDeleteWhenFacultyAreInRepository() {
        facultyRepository.save(faculty1);
        facultyRepository.deleteById(1L);

    }

    @Test
    void readByAge_shouldReturnCollectionWhenFacultiesWithThisColorAreInRepository() {
        Faculty faculty2 = new Faculty(1, "Slizerin", "Green");
        Faculty faculty3 = new Faculty(1, "Kogtevran", "Green");

        facultyRepository.save(faculty1);
        facultyRepository.save(faculty2);
        facultyRepository.save(faculty3);
        assertEquals(2, facultyRepository.findByColor("Green").size());
    }
}