package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.student.StudentPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.student.StudentUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.StudentResponseDTO;
import br.edu.ifpb.ads.easyschool.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping()
    public StudentResponseDTO createStudent(@RequestBody @Valid StudentPostRequestDTO student) {
        var createdStudent = studentService.createStudent(student);
        return createdStudent;
    }


    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(OK)
    @GetMapping("/get-all")
    public Page<StudentResponseDTO> findAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.findAllStudents(pageable);
    }

    @ResponseStatus(OK)
    @GetMapping("login/{login}")
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponseDTO findByUsername(@PathVariable final String username) {
        return studentService.findByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(OK)
    @GetMapping("/{studentId}")
    public StudentResponseDTO findStudentById(@PathVariable Long studentId) {
        return studentService.findStudentById(studentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(OK)
    @PutMapping("/{studentId}") 
    public StudentResponseDTO updateStudent(@PathVariable Long studentId,
            @RequestBody @Valid StudentUpdateRequestDTO student) {
        return studentService.updateStudent(studentId, student);
    }
}
