package br.edu.ifpb.ads.easyschool.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.StudentPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.StudentUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.StudentResponseDTO;
import br.edu.ifpb.ads.easyschool.exception.StudentAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.StudentNotFoundException;
import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.producers.StudentProducer;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final FeeService feeService;
    private final ModelMapper mapper;
    private final StudentProducer studentProducer;


    @Transactional
    public StudentResponseDTO createStudent(StudentPostRequestDTO studentRequestDTO) {
        
        if (studentRepository.findByEmail(studentRequestDTO.getEmail()).isPresent()) {
            throw new StudentAlreadyExistsException("Aluno com email " + studentRequestDTO.getEmail() + " já existe");
        }

        Student createdStudent = mapper.map(studentRequestDTO, Student.class);
        feeService.generateFees(createdStudent);
        createdStudent = studentRepository.save(createdStudent);
        studentProducer.publishMessageEmail(createdStudent);
        createdStudent.setPassword(new BCryptPasswordEncoder().encode(createdStudent.getPassword()));

        return mapper.map(createdStudent, StudentResponseDTO.class);
    }


    public List<StudentResponseDTO> findAllStudents(){
        List<Student> studentsList = studentRepository.findAll();

        List<StudentResponseDTO> studentsListDTO = new ArrayList<>();
        
        for (Student student : studentsList) {
            studentsListDTO.add(mapper.map(student, StudentResponseDTO.class));
        }

        return studentsListDTO;
    }

    public StudentResponseDTO findStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));
        return mapper.map(student, StudentResponseDTO.class);
    }

    public StudentResponseDTO findStudentByEmail(String email){
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));
        return mapper.map(student, StudentResponseDTO.class);
    }

    
    public StudentResponseDTO updateStudent(Long id, StudentUpdateRequestDTO studentUpdateRequestDTO){
        
        Student student = studentRepository.findById(id)
                                     .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));


        student.setEmail(studentUpdateRequestDTO.getEmail());
        student.setPhoneNumber(studentUpdateRequestDTO.getPhoneNumber());
        Student updatedStudent = studentRepository.save(student);
        return mapper.map(updatedStudent, StudentResponseDTO.class);

    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }


    public StudentResponseDTO findByUsername(String name) {
        final var student = studentRepository.findByUsername(name)
                .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));
        return mapper.map(student, StudentResponseDTO.class);
    }


}
