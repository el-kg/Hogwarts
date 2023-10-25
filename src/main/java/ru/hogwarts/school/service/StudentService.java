package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface StudentService {
    Student create(Student student);

    Student read(Long id);

    Student update(Student student);

    Student delete(Long id);

    Collection<Student> readByAge(int age);
}
