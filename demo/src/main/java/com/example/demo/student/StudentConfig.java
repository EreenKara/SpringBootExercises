package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;


// @Import(OtherConfiguration.class) ile harici bir konfigürasyon eklenebilir.
@Configuration // Component üst anotation'un altındaki bir anotation'dur.
// Bunun bir configuration sınıf olduğunu belirtmeye yarıyor
// Configuration içerisinde bean fonksiyonalrı barındırır.
public class StudentConfig {

    @Bean // Bu fonksiyonun döndürdüğü şeyin appContext yani veritabanına ekleneceğini belirtir.
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args -> {
            Student Eren = new Student(
                    "Eren",
                    "erenkara@hotmail.com",
                    LocalDate.of(2001, Month.JANUARY, 1)
            );
            Student Esma = new Student(
                    "Esma",
                    "esmakara@hotmail.com",
                    LocalDate.of(2004, Month.JANUARY, 1)

            );
            repository.saveAll(
                    List.of(Eren,Esma)
            );
        };

    }

}


