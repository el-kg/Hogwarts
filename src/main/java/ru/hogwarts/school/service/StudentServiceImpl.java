package ru.hogwarts.school.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentsRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentsRepository studentsRepository;
    private final Logger logger = (Logger) LoggerFactory.getLogger(AvatarServiceImpl.class);
    boolean switcher = true;

    public StudentServiceImpl(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    @Override
    public Student create(Student student) {
        logger.info("Отработал метод create");
        studentsRepository.findById(student.getId()).orElseThrow(StudentAlreadyExistException::new);
        return studentsRepository.save(student);
    }

    @Override
    public Student read(long id) {
        logger.info("Отработал метод read");
        return studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public Student update(Student student) {
        logger.info("Отработал метод update");
        studentsRepository.findById(student.getId()).orElseThrow(StudentNotFoundException::new);
        studentsRepository.save(student);
        return student;
    }

    @Override
    public Optional<Student> delete(long id) {
        logger.info("Отработал метод delete");
        studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentsRepository.deleteById(id);
        return studentsRepository.findById(id);
    }

    @Override
    public List<Student> findByAge(int age) {
        logger.info("Отработал метод findByAge");
        return studentsRepository.findByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int minAge, int maxAge) {
        logger.info("Отработал метод findByAgeBetween");
        return studentsRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Faculty readStudentFaculty(long studentId) {
        logger.info("Отработал метод readStudentFaculty");
        return read(studentId).getFaculty();
    }

    @Override
    public Collection<Student> readByFacultyId(long facultyId) {
        logger.info("Отработал метод readByFacultyId");
        return studentsRepository.findAllByFaculty_id(facultyId);
    }

    @Override
    public Collection<String> getFilteredByName() {
        logger.info("Отработал метод getFilteredByName");
        return studentsRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Double getAllStudentsByAvgAge() {
        return studentsRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    @Override
    public void getNames() {
        Thread thread1 = new Thread(() -> {
            printName(3L);
            printName(4L);
        });

        Thread thread2 = new Thread(() -> {
            printName(5L);
            printName(6L);
        });

        thread2.start();
        thread1.start();

        printName(1L);
        printName(2L);
    }

    @Override
    public void getNamesSync() {
        Thread thread1 = new Thread(() -> {
            printNameSync(3L);
            printNameSync(4L);
        });

        Thread thread2 = new Thread(() -> {
            printNameSync(5L);
            printNameSync(6L);
        });

        thread1.start();
        thread2.start();

        printNameSync(1L);
        printNameSync(2L);

    }

    @Override
    public void getNamesSync2() {


        while (switcher)
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        printNameSync(3L);
        printNameSync(4L);
        switcher = false;
        notify();

        while (!switcher)
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.getMessage();
            }
        printNameSync(5L);
        printNameSync(6L);

        printNameSync(1L);
        printNameSync(2L);
    }


    private void printName(long id) {
        String name = studentsRepository.findById(id).get().getName();
        System.out.println(name);
    }

    private synchronized void printNameSync(long id) {
        String name = studentsRepository.findById(id).get().getName();
        System.out.println(name);
    }


}
