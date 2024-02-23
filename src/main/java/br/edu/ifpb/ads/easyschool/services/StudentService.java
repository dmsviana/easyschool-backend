package br.edu.ifpb.ads.easyschool.services;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ads.easyschool.dtos.response.StudentResponseDTO;
import br.edu.ifpb.ads.easyschool.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.dtos.request.StudentRequestDTO;
import br.edu.ifpb.ads.easyschool.dtos.request.StudentUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.exception.StudentAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.StudentNotFoundException;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentService(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO studentRequestDTO) {
        
        if (studentRepository.findByEmail(studentRequestDTO.getEmail()).isPresent()) {
            throw new StudentAlreadyExistsException("Aluno com email " + studentRequestDTO.getEmail() + " já existe");
        }
    
        Student createdStudent = mapper.map(studentRequestDTO, Student.class);
        createdStudent = studentRepository.save(createdStudent);
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


    public boolean isCurrentUser(Authentication authentication, Long studentId) {
        Long currentUserId = Long.valueOf(authentication.getName());
        return studentId.equals(currentUserId);
    }

}
