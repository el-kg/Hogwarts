package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import javax.swing.text.Position;
import java.util.Collection;
import java.util.List;

public interface StudentsRepository extends JpaRepository<Student, Long> {
    List<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int minAge,int maxAge);
    Collection<Student> findAllByFaculty_id(long faculty_id);

}
