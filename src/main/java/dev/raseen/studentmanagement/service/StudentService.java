package dev.raseen.studentmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.raseen.studentmanagement.entity.Student;
import dev.raseen.studentmanagement.exception.StudentNotFoundException;
import dev.raseen.studentmanagement.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final EmailService emailService;

    public StudentService(StudentRepository studentRepository, EmailService emailService) {
        this.studentRepository = studentRepository;
        this.emailService = emailService;
    }

    @Cacheable("allStudents")
    public List<Student> getAllStudentsFromCache() {
        return studentRepository.findAll();
    }

    public Page<Student> getAllStudents(int page, int size) {
        List<Student> students = getAllStudentsFromCache();

        int start = page * size;
        if (start >= students.size()) {
            return new PageImpl<>(List.of(), PageRequest.of(page, size), students.size());
        }

        int end = Math.min(start + size, students.size());
        List<Student> pagedStudents = students.subList(start, end);
        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<>(pagedStudents, pageable, students.size());
    }

    @Cacheable(value = "studentById", key = "#id")
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not fount with id " + id));
    }

    @CacheEvict(value = { "allStudents", "studentsPage" }, allEntries = true)
    public Student createStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        try {
            emailService.sendStudentWelcomeEmail(
                    savedStudent.getEmail(),
                    savedStudent.getName(),
                    savedStudent.getCourse());
        } catch (Exception e) {
            log.error("Failed to send welcome email", e);
        }

        return savedStudent;
    }

    @CacheEvict(value = { "allStudents", "studentsPage" }, allEntries = true)
    public List<Student> createStudents(List<Student> students) {
        List<Student> savedStudents = studentRepository.saveAll(students);

        savedStudents.forEach(student -> {
            try {
                emailService.sendStudentWelcomeEmail(
                        student.getEmail(),
                        student.getName(),
                        student.getCourse());
                Thread.sleep(1000); //handling to mailtrap basic plan
            } catch (Exception e) {
                log.error(
                        "Failed to send email to {}",
                        student.getEmail(),
                        e);
            }
        });

        return savedStudents;
    }

    @Caching(put = {
            @CachePut(value = "studentById", key = "#result.id")
    }, evict = {
            @CacheEvict(value = "allStudents", allEntries = true),
            @CacheEvict(value = "studentsPage", allEntries = true)
    })
    public Student updateStudent(Long id, Student student) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));

        if (student.getName() != null) {
            existing.setName(student.getName());
        }

        if (student.getEmail() != null) {
            existing.setEmail(student.getEmail());
        }

        return studentRepository.save(existing);
    }

    @Caching(evict = {
            @CacheEvict(value = "studentById", key = "#id"),
            @CacheEvict(value = "allStudents", allEntries = true),
            @CacheEvict(value = "studentsPage", allEntries = true)
    })
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not fount with id " + id);
        }
        studentRepository.deleteById(id);
    }
}
