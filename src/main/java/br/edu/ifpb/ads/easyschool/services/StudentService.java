package br.edu.ifpb.ads.easyschool.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.student.StudentPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.student.StudentUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.ManagerResponseDTO;
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
    public ManagerResponseDTO createStudent(StudentPostRequestDTO studentRequestDTO) {
        
        if (studentRepository.findByEmail(studentRequestDTO.getEmail()).isPresent()) {
            throw new StudentAlreadyExistsException("Aluno com email " + studentRequestDTO.getEmail() + " já existe");
        }

        Student createdStudent = mapper.map(studentRequestDTO, Student.class);
        feeService.generateFees(createdStudent);
        createdStudent = studentRepository.save(createdStudent);
        studentProducer.publishMessageEmail(createdStudent);
        createdStudent.setPassword(new BCryptPasswordEncoder().encode(createdStudent.getPassword()));

        return mapper.map(createdStudent, ManagerResponseDTO.class);
    }


    public List<ManagerResponseDTO> findAllStudents(){
        List<Student> studentsList = studentRepository.findAll();

        List<ManagerResponseDTO> studentsListDTO = new ArrayList<>();
        
        for (Student student : studentsList) {
            studentsListDTO.add(mapper.map(student, ManagerResponseDTO.class));
        }

        return studentsListDTO;
    }

    public ManagerResponseDTO findStudentById(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));
        return mapper.map(student, ManagerResponseDTO.class);
    }

    public ManagerResponseDTO findStudentByEmail(String email){
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));
        return mapper.map(student, ManagerResponseDTO.class);
    }

    
    public ManagerResponseDTO updateStudent(Long id, StudentUpdateRequestDTO studentUpdateRequestDTO){
        
        Student student = studentRepository.findById(id)
                                     .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));


        student.setEmail(studentUpdateRequestDTO.getEmail());
        student.setPhoneNumber(studentUpdateRequestDTO.getPhoneNumber());
        Student updatedStudent = studentRepository.save(student);
        return mapper.map(updatedStudent, ManagerResponseDTO.class);

    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }


    public ManagerResponseDTO findByUsername(String name) {
        final var student = studentRepository.findByUsername(name)
                .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));
        return mapper.map(student, ManagerResponseDTO.class);
    }


}
