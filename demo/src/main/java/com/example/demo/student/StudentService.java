package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service // Component üst anotation'un altındaki bir anotation'dur.
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent()){
            throw new IllegalStateException("email zaten kayıtlı");
        }
        // .net'e kıyasla burada ekleme işlemleri save ile oluyor Add ile değil.
        studentRepository.save(student);


    }


    public void deleteStudent(Long studentId) {
        boolean varmi = studentRepository.existsById(studentId);
        if(!varmi)
        {
            throw  new IllegalStateException(String.format("%ld numaralı id'ye ait öğrenci bulunamadı.",studentId));
        }
        studentRepository.deleteById(studentId);
    }
    @Transactional
    public void updateStudent(Student student){

        if(student.getId()==null){
            throw new IllegalStateException("Hocam düzgün kullanın şu API'yı. Id gir");
        }
        // Kendime not findById'ye boş vir id değeri verisen exception fırlatıyor - ki doğal yani -
        // Bunu ise API'yı consume ederken görüyorum yani şunu diyorum Binding yapılırken id eşleşmesi olmaz ise
        // null değeri atanıyor. Sıkıntı çıkarmıyor Binding mekanızması.
        // Benim kod içerisinde Business Logic'te bu durum kontorl etmem gerekiyor.
        Optional<Student> varmi = studentRepository.findById(student.getId());

        if(student.getId()==null){
            throw new IllegalStateException("Id girsene lan");
        }
        if(varmi.isEmpty()){
            throw new IllegalStateException(String.format("%ld id'li öğrenci bulunamadı.",student.getId()));
        }
        Student ogrenci = varmi.get();
        ogrenci.setName(student.getName());
        ogrenci.setDob(student.getDob());
        ogrenci.setEmail(student.getEmail());
    }
    // Transactional bizler için update işlemlerinde sorumluluğu üzerine alıyor.
    // Bizler hiç reposityory'ye gidip update yapmadan öğrencinin verilerini güncelleyebildik.
    // .Net'teki direkt olarak .save() methodunu çağırmaya benziyor.
    // Ancak burada onuda yapmadık ki burada save() methodu veri eklemek için kullanılıyor.
    @Transactional
    public void updateStudentAmigo(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()-> new IllegalStateException(String.format("%ld id'li öğrenci bulunamadı",studentId)));
        if(name!=null && name.length()>0&&
            !Objects.equals(student.getName(),name)){
            student.setName(name);
        }
        if(email!=null && email.length()>0&&
                !Objects.equals(student.getEmail(),email)){
            Optional<Student> varmi=studentRepository.findStudentByEmail(email);
            if(varmi.isPresent())
            {
                throw new IllegalStateException("email zaten var");
            }
            student.setEmail(email);
        }
    }
}
