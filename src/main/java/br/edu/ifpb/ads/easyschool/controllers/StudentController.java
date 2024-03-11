package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.StudentPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.StudentUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.StudentResponseDTO;
import br.edu.ifpb.ads.easyschool.services.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper mapper;

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
    public List<StudentResponseDTO> findAllStudents() {
        List<StudentResponseDTO> responseList = studentService.findAllStudents();
        return responseList;
    }

    @ResponseStatus(OK)
    @GetMapping("login/{login}")
    @PreAuthorize("hasRole('ADMIN')")
    public StudentResponseDTO findByUsername(@PathVariable final String username) {
        final var student = studentService.findByUsername(username);
        return mapper.map(student, StudentResponseDTO.class);
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
