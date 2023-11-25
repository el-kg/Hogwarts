package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import javax.swing.text.Position;
import java.util.Collection;
import java.util.List;

public interface StudentsRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    Collection<Student> findAllByFaculty_id(long faculty_id);

    @Query(value = "SELECT COUNT(*)FROM student", nativeQuery = true)
    Integer getCountOfAllStudents();

    @Query(value = "SELECT AVG(age)FROM student", nativeQuery = true)
    Double getAverageAgeOfStudents();

    @Query(value = "SELECT*FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();

}
