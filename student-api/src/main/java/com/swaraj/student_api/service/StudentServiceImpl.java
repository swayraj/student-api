package com.swaraj.student_api.service;

import com.swaraj.student_api.dao.StudentRepository;
import com.swaraj.student_api.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    //define field
    private StudentRepository studentRepository;

    //setup constructor injection
    @Autowired
    private StudentServiceImpl(StudentRepository theStudentRepository)
    {
        studentRepository = theStudentRepository;
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(int theId) {
        Optional<Student> result = studentRepository.findById(theId);

        Student theStudent = null;
        if(result.isPresent())
        {
            theStudent = result.get();
        }
        else{
            throw new RuntimeException("Student Not found -" + theId);
        }

        return theStudent;
    }

    @Override
    public Student save(Student theStudent) {
        return studentRepository.save(theStudent);
    }

    @Override
    public void deleteById(int theId) {
        studentRepository.deleteById(theId);

    }
}