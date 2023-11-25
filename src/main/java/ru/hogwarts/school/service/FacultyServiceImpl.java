package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistException;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Service

public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final Logger logger= (Logger) LoggerFactory.getLogger(AvatarServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Отработал метод create");
        facultyRepository.findById(faculty.getId()).orElseThrow(FacultyAlreadyExistException::new);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        logger.info("Отработал метод read");
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    @Override
    public Faculty update(Faculty faculty) {
        logger.info("Отработал метод update");
        facultyRepository.findById(faculty.getId()).orElseThrow(FacultyNotFoundException::new);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        logger.info("Отработал метод delete");
        Faculty f = facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
        facultyRepository.deleteById(id);
        return f;
    }

    @Override
    public List<Faculty> findByColor(String color) {
        logger.info("Отработал метод findByColor");
        return facultyRepository.findByColor(color);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String name, String color) {
        logger.info("Отработал метод findByColorOrName");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }
}

