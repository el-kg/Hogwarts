package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentsRepository studentsRepository;

    public StudentServiceImpl(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public Student create(Student student) {
        return studentsRepository.save(student);
    }

    @Override
    public Student read(long id) {
        Optional<Student> student = studentsRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException("Такой студент тут не учится");
        }
        return student.get();
    }

    @Override
    public Student update(Student student) {
        if (studentsRepository.findById(student.getId()).isEmpty()) {
            throw new StudentNotFoundException("Такой студент тут не учится");
        }
        return studentsRepository.save(student);
    }

    @Override
    public Student delete(long id) {
        Student student = studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentsRepository.deleteById(id);
        return student;
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return studentsRepository.findByAge(age);
    }
}
