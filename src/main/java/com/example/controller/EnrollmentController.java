package com.example.controller;

import com.example.entity.Enrollment;
import com.example.entity.Studententity;
import com.example.entity.Course;

import com.example.Repository.EnrollmentRepository;
import com.example.Repository.StudentRepository;
import com.example.Repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    // 1. GET ALL ENROLLMENTS
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }


    // 2. GET ENROLLMENT BY ID
    @GetMapping("/{id}")
    public Optional<Enrollment> getEnrollmentById(
            @PathVariable Long id) {

        return enrollmentRepository.findById(id);
    }


    // 3. CREATE / ENROLL STUDENT
    @PostMapping
    public String createEnrollment(
            @RequestBody Enrollment enrollment) {

        // Check whether student exists
        if (!studentRepository.existsById(enrollment.getStudentId())) {
            return "Student not found";
        }

        // Check whether course exists
        if (!courseRepository.existsById(enrollment.getCourseId())) {
            return "Course not found";
        }

        // Check duplicate enrollment
        List<Enrollment> enrollments = enrollmentRepository.findAll();

        for (Enrollment existingEnrollment : enrollments) {

            if (existingEnrollment.getStudentId()
                    .equals(enrollment.getStudentId())
                    && existingEnrollment.getCourseId()
                    .equals(enrollment.getCourseId())) {

                return "Student already enrolled";
            }
        }

        // Save enrollment
        enrollmentRepository.save(enrollment);

        return "Enrollment successful";
    }


    // 4. UPDATE ENROLLMENT
    @PutMapping("/{id}")
    public String updateEnrollment(
            @PathVariable Long id,
            @RequestBody Enrollment enrollmentDetails) {

        Optional<Enrollment> optionalEnrollment =
                enrollmentRepository.findById(id);

        if (optionalEnrollment.isEmpty()) {
            return "Enrollment not found";
        }

        Enrollment enrollment = optionalEnrollment.get();

        // Check student
        if (!studentRepository.existsById(
                enrollmentDetails.getStudentId())) {

            return "Student not found";
        }

        // Check course
        if (!courseRepository.existsById(
                enrollmentDetails.getCourseId())) {

            return "Course not found";
        }

        // Check duplicate enrollment
        List<Enrollment> enrollments =
                enrollmentRepository.findAll();

        for (Enrollment existingEnrollment : enrollments) {

            if (!existingEnrollment.getEnrollmentId()
                    .equals(id)
                    && existingEnrollment.getStudentId()
                    .equals(enrollmentDetails.getStudentId())
                    && existingEnrollment.getCourseId()
                    .equals(enrollmentDetails.getCourseId())) {

                return "Student already enrolled";
            }
        }

        enrollment.setStudentId(enrollmentDetails.getStudentId());
        enrollment.setCourseId(enrollmentDetails.getCourseId());
        enrollment.setEnrollmentDate(
                enrollmentDetails.getEnrollmentDate());
        enrollment.setStatus(enrollmentDetails.getStatus());

        enrollmentRepository.save(enrollment);

        return "Enrollment updated successfully";
    }


    // 5. DELETE ENROLLMENT
    @DeleteMapping("/{id}")
    public String deleteEnrollment(
            @PathVariable Long id) {

        if (!enrollmentRepository.existsById(id)) {
            return "Enrollment not found";
        }

        enrollmentRepository.deleteById(id);

        return "Enrollment deleted successfully";
    }
}