package br.edu.ifpb.ads.easyschool.controllers;

import java.util.List;

import br.edu.ifpb.ads.easyschool.dtos.response.StudentResponseDTO;
import br.edu.ifpb.ads.easyschool.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpb.ads.easyschool.dtos.request.StudentRequestDTO;
import br.edu.ifpb.ads.easyschool.dtos.request.StudentUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.services.StudentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody @Valid StudentRequestDTO student){
        String encryptedPassword = new BCryptPasswordEncoder().encode(student.getPassword());
        student.setPassword(encryptedPassword);

        var createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<StudentResponseDTO>> findAllStudents(){
        List<StudentResponseDTO> responseList = studentService.findAllStudents();
        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> findStudentById(@PathVariable Long studentId){
        StudentResponseDTO student = studentService.findStudentById(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    //@PreAuthorize("@studentService.isCurrentUser(principal, #studentId)")
    @PutMapping("/{studentId}") // Only allow updates to self
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long studentId, @RequestBody @Valid StudentUpdateRequestDTO student){
        StudentResponseDTO updatedStudent = studentService.updateStudent(studentId, student);
        return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);
    }
}
