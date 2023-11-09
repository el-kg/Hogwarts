package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.Collection;
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
        studentsRepository.findById(student.getId()).orElseThrow(StudentAlreadyExistException::new);
        return studentsRepository.save(student);
        //return student;
    }

    @Override
    public Student read(long id) {
        return studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public Student update(Student student) {
        studentsRepository.findById(student.getId()).orElseThrow(StudentNotFoundException::new);
        studentsRepository.save(student);
        return student;
    }

    @Override
    public Optional<Student> delete(long id) {
        studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentsRepository.deleteById(id);
        return studentsRepository.findById(id);
    }

    @Override
    public List<Student> findByAge(int age) {
        return studentsRepository.findByAge(age);
    }
    @Override
    public Collection<Student> findByAgeBetween(int minAge,int maxAge){
        return studentsRepository.findByAgeBetween(minAge,maxAge);}
    @Override
    public Faculty readStudentFaculty(long studentId){
        return read(studentId).getFaculty();
    }
    @Override
    public Collection<Student> readByFacultyId(long facultyId){
        return studentsRepository.findAllByFaculty_id(facultyId);}
}
