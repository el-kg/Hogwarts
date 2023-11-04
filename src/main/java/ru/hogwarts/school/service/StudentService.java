package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student create(Student student);

    Student read(long id);

    Student update(Student student);

    Optional<Student> delete(long id);

    List<Student> findByAge(int age);
}
