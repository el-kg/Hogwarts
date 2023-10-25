package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty create(Faculty faculty);

    Faculty read(Long id);

    Faculty update(Faculty faculty);

    Faculty delete(Long id);

    Collection<Faculty> readByColor(String color);
}
