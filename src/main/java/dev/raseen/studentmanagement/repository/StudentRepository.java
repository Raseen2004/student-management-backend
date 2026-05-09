package dev.raseen.studentmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.raseen.studentmanagement.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
