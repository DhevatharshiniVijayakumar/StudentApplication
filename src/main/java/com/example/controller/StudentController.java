
package com.example.controller;

import com.example.entity.Studententity;
import com.example.Repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // GET all students
    @GetMapping
    public List<Studententity> getAllStudents() {
        return studentRepository.findAll();
    }

    // GET student by ID
    @GetMapping("/{id}")
    public Optional<Studententity> getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id);
    }

    // POST - Add student
    @PostMapping
    public Studententity addStudent(@RequestBody Studententity student) {
        return studentRepository.save(student);
    }

    // PUT - Update student
    @PutMapping("/{id}")
    public Studententity updateStudent(
            @PathVariable Long id,
            @RequestBody Studententity studentDetails) {

        Studententity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setDepartment(studentDetails.getDepartment());
        student.setAge(studentDetails.getAge());

        return studentRepository.save(student);
    }

    // DELETE - Delete student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {

        if (!studentRepository.existsById(id)) {
            return "Student not found";
        }

        studentRepository.deleteById(id);

        return "Student deleted successfully";
    }
}