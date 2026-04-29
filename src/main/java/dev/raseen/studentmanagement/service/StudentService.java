package dev.raseen.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.raseen.studentmanagement.entity.Student;
import dev.raseen.studentmanagement.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private  StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> createStudents(List<Student> students) {
        return studentRepository.saveAll(students);
    }


    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
