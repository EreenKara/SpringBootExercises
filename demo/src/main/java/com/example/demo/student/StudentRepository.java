package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Component üst anotation'un altındaki bir anotation'dur.
public interface StudentRepository  extends JpaRepository<Student,Long> {


    // burada yazılan query jpql , sql değil.
    // aşağıdaki Student olan bizim ajva içerisindeki enity'miz
    // Aşağıdaki fonksyion çağırılınca çalışması gerekn query'yi belirttik.
    @Query("SELECT s FROM Student s WHERE s.email=?1")

    Optional<Student> findStudentByEmail(String email);


}
