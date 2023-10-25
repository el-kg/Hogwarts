package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> repository = new HashMap<>();
    private Long countId = 0L;

    @Override
    public Faculty create(Faculty faculty) {
        if (repository.containsValue(faculty)) {
            throw new FacultyAlreadyExistException("Такой факультет уже есть!");
        }
        Long id = ++countId;
        faculty.setId(id);
        repository.put(id, faculty);
        return faculty;
    }

    @Override
    public Faculty read(Long id) {
        Faculty faculty = repository.get(id);
        if (faculty == null) {
            throw new FacultyNotFoundException("Такого факультета нет в Хогвартсе!");
        }
        return faculty;
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!repository.containsKey(faculty.getId())) {
            throw new FacultyNotFoundException("Такого факультета нет в Хогвартсе!");
        }
        repository.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty delete(Long id) {
        Faculty faculty = repository.remove(id);
        if (faculty == null) {
            throw new FacultyNotFoundException("Такого факультета нет в Хогвартсе!");
        }
        return faculty;
    }

    @Override
    public Collection<Faculty> readByColor(String color) {
        return repository.values().stream()
                .filter(faculty -> faculty.getColor() == color)
                .collect(Collectors.toUnmodifiableList());
    }
}

