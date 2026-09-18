package com.example.controller;

import com.example.entity.Course;
import com.example.Repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    // 1. Get all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 2. Get course by ID
    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id);
    }

    // 3. Create new course
    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    // 4. Update existing course
    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable Long id,
            @RequestBody Course courseDetails) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setCourseName(courseDetails.getCourseName());
        course.setDepartment(courseDetails.getDepartment());
        course.setDuration(courseDetails.getDuration());
        course.setFees(courseDetails.getFees());

        return courseRepository.save(course);
    }

    // 5. Delete course
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id) {

        if (!courseRepository.existsById(id)) {
            return "Course not found";
        }

        courseRepository.deleteById(id);

        return "Course deleted successfully";
    }
}
