package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;
import java.util.Optional;

@Service

public class FacultyServiceImpl implements FacultyService {
    FacultyRepository facultyRepository;
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
                    return facultyRepository.save(faculty);
    }

    @Override
    public Optional<Faculty> read(long id) {
      return facultyRepository.findById(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public void delete(long id) {
        facultyRepository.deleteById(id);
    }

   @Override
    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }
}

