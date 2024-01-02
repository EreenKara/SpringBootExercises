package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



// Eğerki jar'ı çalıştırırken multiple proje çalıştırmak istiyorsan port'u belirtebilirsin.
// java -jar .\demo-0.0.1-SNAPSHOT.jar  --server.port=8081
// yukarıdaki gibi yapınca çıktı aldığın jar çalışacak ve 8081 portu üzerinden ayağa kalkacak.
@RestController // bunun bir controler sınıfı olduğnu beltrtmek için kullanıyoruz.
@RequestMapping(path = "api/v1/student")  // route belirlemeye yarıyor.
public class StudentController {

    private final StudentService studentService;

    @Autowired // Bu işlem dependency injection yapıldığını belirtmek için gerekli.
    // Ayrıca bu sınfı nerden injecte edeceğini belirtmek için sınıfın başına @Component ekle
    // ya da @Repository veya @Service 'de ekleyebilirsin..
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping // Get mapping Get http methoduna cevap vermesini sağlıyor
    public List<Student> getStudents(){
        return studentService.getStudents();
    }
    @GetMapping(path="/hello")
    public String hello(){
        return "Hello World";
    }
    @PostMapping
    public void registerNewStudent(@RequestBody Student student)
    {
        studentService.addNewStudent(student);
    }
    // aşağıdaki {} içerisinde belirttiğimiz studentId bir route variable
    // onu query variable ile karıştırma.
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
    // Benim yaptığım
    @PutMapping
    public void updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
    }
    // AmigosCode'un yaptığı
    @PutMapping(path = "{studentId}")
    public void updateStudentAmigo(@PathVariable("studentId") Long studentId,
                                   @RequestParam(required = false) String name, // Request param query string ile verilen parametre oluyor.
                                   @RequestParam(required = false) String email){
        studentService.updateStudentAmigo(studentId,name,email);
    }


}




