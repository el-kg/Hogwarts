package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
          studentService.delete(id);
         return studentService.read(id);
    }

    @GetMapping
    public Collection<Student> readByAge( @RequestParam int age) {
        return studentService.findByAge(age);
    }
    @GetMapping("/age")
    public Collection<Student> readByAgeBetween( @RequestParam int minAge,@RequestParam int maxAge){
        return studentService.findByAgeBetween(minAge,maxAge);
    }
    @GetMapping("/faculty/{id}")
    public Faculty readStudentFaculty(@PathVariable long id){ return studentService.readStudentFaculty(id);}
    @GetMapping("/students/{id}")
    public Collection<Student> readAllByFacultyId( @PathVariable long id){return studentService.readByFacultyId(id);}

}
