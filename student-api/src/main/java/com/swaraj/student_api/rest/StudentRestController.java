package com.swaraj.student_api.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swaraj.student_api.entity.Student;
import com.swaraj.student_api.service.OllamaService;
import com.swaraj.student_api.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    private StudentService studentService;

    private ObjectMapper objectMapper;

    private OllamaService ollamaService;

    //inject student dao (use constructor injection)
    public StudentRestController(StudentService theStudentService,
                                 ObjectMapper theObjectMapper,
                                 OllamaService theOllamaService)
    {
        studentService = theStudentService;
        objectMapper = theObjectMapper;
        ollamaService = theOllamaService;
    }

    //expose POST endpoint to insert a new Student
    @PostMapping
    public Student addStudent(@Valid @RequestBody Student theStudent)
    {
        //in case the user passes an id in json
        theStudent.setID(0);

        Student dbStudent = studentService.save(theStudent);

        return dbStudent;
    }

    //expose GET "/students" and a return list of all students
    @GetMapping
    public List<Student> findAll()
    {
        return studentService.findAll();
    }

    //expose GET "/students/{id}" to return student by their id
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id)
    {
        Student theStudent = studentService.findById(id);

        if(theStudent == null)
        {
            throw new RuntimeException("Student Id Not Found -" + id);
        }

        return theStudent;
    }

    //expose PUT "/students/{id} to update a student their id"
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable int id,
            @RequestBody Student theStudent)
    {
        Student existingStudent = studentService.findById(id);

        if (existingStudent == null) {
            throw new RuntimeException("Student Id Not Found - " + id);
        }

        Student dbStudent = studentService.save(theStudent);
        return dbStudent;
    }

    //expose DELETE "/students/{id}" to delete a student by their id
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id)
    {
        Student tempStudent = studentService.findById(id);

        if(tempStudent == null)
        {
            throw new RuntimeException("Student not found" + id);
        }
        studentService.deleteById(id);

        return "Student Deleted " + id;
    }

    //expose GET "/students/{id}/summary" to get AI summary
    @GetMapping("/{id}/summary")
    public String getStudentSummary(@PathVariable int id) {
        try {
            Student student = studentService.findById(id);
            if (student == null) {
                throw new RuntimeException("Student not found: " + id);
            }
            String studentDetails = String.format("%s (Age: %d, Email: %s)",
                    student.getName(), student.getAge(), student.getEmail());
            return ollamaService.generateSummary(studentDetails);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate summary: " + e.getMessage());
        }
    }
}