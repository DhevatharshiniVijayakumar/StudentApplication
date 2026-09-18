package com.example.controller;

import com.example.entity.Marks;
import com.example.Repository.MarksRepository;
import com.example.Repository.StudentRepository;
import com.example.Repository.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marks")
public class MarksController {

    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;


    // GET all marks
    @GetMapping
    public List<Marks> getAllMarks() {
        return marksRepository.findAll();
    }


    // GET marks by ID
    @GetMapping("/{id}")
    public Optional<Marks> getMarksById(@PathVariable Long id) {
        return marksRepository.findById(id);
    }


    // POST - Add marks
    @PostMapping
    public String createMarks(@RequestBody Marks marks) {

        // Check student exists
        if (!studentRepository.existsById(marks.getStudentId())) {
            return "Student not found";
        }

        // Check course exists
        if (!courseRepository.existsById(marks.getCourseId())) {
            return "Course not found";
        }

        // Marks cannot be negative
        if (marks.getMarks() < 0) {
            return "Marks cannot be negative";
        }

        // Marks cannot be greater than total marks
        if (marks.getMarks() > marks.getTotalMarks()) {
            return "Marks cannot be greater than total marks";
        }

        marksRepository.save(marks);

        return "Marks added successfully";
    }


    // PUT - Update marks
    @PutMapping("/{id}")
    public String updateMarks(
            @PathVariable Long id,
            @RequestBody Marks marksDetails) {

        Optional<Marks> optionalMarks = marksRepository.findById(id);

        if (optionalMarks.isEmpty()) {
            return "Marks not found";
        }

        // Check student exists
        if (!studentRepository.existsById(marksDetails.getStudentId())) {
            return "Student not found";
        }

        // Check course exists
        if (!courseRepository.existsById(marksDetails.getCourseId())) {
            return "Course not found";
        }

        // Marks cannot be negative
        if (marksDetails.getMarks() < 0) {
            return "Marks cannot be negative";
        }

        // Marks cannot be greater than total marks
        if (marksDetails.getMarks() > marksDetails.getTotalMarks()) {
            return "Marks cannot be greater than total marks";
        }

        Marks marks = optionalMarks.get();

        marks.setStudentId(marksDetails.getStudentId());
        marks.setCourseId(marksDetails.getCourseId());
        marks.setExamName(marksDetails.getExamName());
        marks.setMarks(marksDetails.getMarks());
        marks.setTotalMarks(marksDetails.getTotalMarks());

        marksRepository.save(marks);

        return "Marks updated successfully";
    }


    // DELETE marks
    @DeleteMapping("/{id}")
    public String deleteMarks(@PathVariable Long id) {

        if (!marksRepository.existsById(id)) {
            return "Marks not found";
        }

        marksRepository.deleteById(id);

        return "Marks deleted successfully";
    }
}
