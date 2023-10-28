package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private  final StudentsRepository studentsRepository;

    public StudentServiceImpl(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public Student create(Student student) {
        return studentsRepository.save(student);
    }

    @Override
    public Optional<Student> read(long id) {
        return studentsRepository.findById(id);
    }

    @Override
    public Student update(Student student) {
       return  studentsRepository.save(student);
    }

    @Override
    public void delete(long id) {
        studentsRepository.deleteById(id);
    }

    @Override
    public List<Student> findByAge(int age) {
        return studentsRepository.findByAge(age);
    }
}
