package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.List;

@Service

public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
        facultyRepository.findById(faculty.getId()).orElseThrow(FacultyAlreadyExistException::new);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
      return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public Faculty update(Faculty faculty) {
        facultyRepository.findById(faculty.getId()).orElseThrow(FacultyNotFoundException::new);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        Faculty f =facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.deleteById(id);
        return f;
    }

   @Override
    public List<Faculty> findByColor(String color) {
        return facultyRepository.findByColor(color);
    }
    @Override
    public Collection<Faculty> findByColorOrName(String name,String color){
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name,color);}
    }

