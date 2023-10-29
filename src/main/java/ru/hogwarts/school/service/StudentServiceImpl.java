package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private  final StudentsRepository studentsRepository;

    public StudentServiceImpl(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public Student create(Student student) {
        studentsRepository.findById(student.getId()).orElseThrow(StudentAlreadyExistException::new);
        return studentsRepository.save(student);
    }

    @Override
    public Student read(long id) {
        return studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public Student update(Student student) {
        studentsRepository.findById(student.getId()).orElseThrow(StudentNotFoundException::new);
        return  studentsRepository.save(student);
    }

    @Override
    public Student delete(long id) {
        Student s = studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentsRepository.deleteById(id);
        return s;
    }

    @Override
    public List<Student> findByAge(int age) {
        return studentsRepository.findByAge(age);
    }
}
