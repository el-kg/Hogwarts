package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface  FacultyService {
    Faculty create(Faculty faculty);

    Optional<Faculty> read(long id);

    Faculty update(Faculty faculty);

    void delete(long id);

    List<Faculty> findByColor(String color);
}
