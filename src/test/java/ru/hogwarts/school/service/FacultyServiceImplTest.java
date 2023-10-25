package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {

    FacultyService facultyService = new FacultyServiceImpl();
    Faculty faculty1 = new Faculty(1, "Griffindor", "Yellow");

    @Test
    void create_shouldCreateAndReturnFaculty() {
        assertEquals(faculty1, facultyService.create(faculty1));
    }

    @Test
    void create_shouldThrowFacultyAlreadyExistExceptionWhenFacultyAreAlreadyInMap() {
        Faculty expectedFaculty = new Faculty(1, "Griffindor", "Yellow");
        facultyService.create(expectedFaculty);
        assertThrows(FacultyAlreadyExistException.class, () -> facultyService.create(faculty1));
    }

    @Test
    void read_shouldReadAndReturnFaculty() {
        facultyService.create(faculty1);
        assertEquals(faculty1, facultyService.read(1L));
    }

    @Test
    void read_shouldThrowFacultyNotFoundException() {
        assertThrows(FacultyNotFoundException.class, () -> facultyService.read(1L));
    }

    @Test
    void update_shouldReturnUpdateFacultyWhenFacultyAreInMap() {
        facultyService.create(faculty1);
        Faculty expectedFaculty = new Faculty(1, "Griffindor", "Yellow");
        assertEquals(expectedFaculty, facultyService.update(expectedFaculty));
    }

    @Test
    void update_shouldThrowFacultyNotFoundExceptionWhenFacultyAreNotInMap() {
        assertThrows(FacultyNotFoundException.class, () -> facultyService.update(faculty1));
    }

    @Test
    void delete_shouldThrowFacultyNotFoundExceptionWhenFacultyAreNotInMap() {
        assertThrows(FacultyNotFoundException.class, () -> facultyService.delete(1L));
    }

    @Test
    void delete_shouldDeleteAndReturnFacultyWhenStudentAreInMap() {
        facultyService.create(faculty1);
        assertEquals(faculty1, facultyService.delete(1L));
    }

    @Test
    void readByAge_shouldReturnCollectionWhenFacultysWithThisColorAreInMap() {
        Faculty faculty2 = new Faculty(1, "Slizerin", "Green");
        Faculty faculty3 = new Faculty(1, "Kogtevran", "Green");

        facultyService.create(faculty1);
        facultyService.create(faculty2);
        facultyService.create(faculty3);
        assertEquals(2, facultyService.readByColor("Green").size());
    }
}