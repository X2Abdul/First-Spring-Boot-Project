package com.abdul;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.beans.Customizer;
import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/students")
public class Main {
    private final StudentRepository studentRepository;

    public Main(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    record NewStudentRequest(
            String name,
            String email,
            Integer age
    ){}

    @PostMapping
    public void addStudent(@RequestBody NewStudentRequest request){
        Student student = new Student();
        student.setName(request.name);
        student.setAge(request.age);
        student.setEmail(request.email);
        studentRepository.save(student);
    }

    @DeleteMapping("{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer id){
        if(studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }

    }

    record NewStudentDetails(
            Integer id,
            String name,
            String email,
            Integer age
    ){}
    @PutMapping
    public void changeStudentDetails(@RequestBody NewStudentDetails details){
        Student student = studentRepository.findById(details.id).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + details.id));
        student.setName(details.name);
        student.setEmail(details.email);
        student.setAge(details.age);
        studentRepository.save(student);
    }
}
