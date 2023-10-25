package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> repository = new HashMap<>();
    private Long countId = 0L;

    @Override
    public Student create(Student student) {
        if (repository.containsValue(student)) {
            throw new StudentAlreadyExistException("Такой студент уже есть в списках!");
        }
        Long id = ++countId;
        student.setId(id);
        repository.put(id, student);
        return student;
    }

    @Override
    public Student read(Long id) {
        Student student = repository.get(id);
        if (student == null) {
            throw new StudentNotFoundException("Такого студента нет в списках!");
        }
        return student;
    }

    @Override
    public Student update(Student student) {
        if (!repository.containsKey(student.getId())) {
            throw new StudentNotFoundException("Такого студента нет в списках!");
        }
        repository.put(student.getId(), student);
        return student;
    }

    @Override
    public Student delete(Long id) {
        Student student = repository.remove(id);
        if (student == null) {
            throw new StudentNotFoundException("Такого студента нет в списках!");
        }
        return student;
    }

    @Override
    public Collection<Student> readByAge(int age) {
        return repository.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toUnmodifiableList());
    }
}
