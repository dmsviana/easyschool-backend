package br.edu.ifpb.ads.easyschool.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.teacher.TeacherPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.teacher.TeacherUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.LessonResponseDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.TeacherResponseDTO;
import br.edu.ifpb.ads.easyschool.exception.TeacherAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.TeacherNotFoundException;
import br.edu.ifpb.ads.easyschool.model.Lesson;
import br.edu.ifpb.ads.easyschool.model.Teacher;
import br.edu.ifpb.ads.easyschool.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonService lessonService;
    private final ModelMapper mapper;

    @Transactional
    public TeacherResponseDTO createTeacher(TeacherPostRequestDTO teacherRequestDTO) {
        if (teacherRepository.findByEmail(teacherRequestDTO.getEmail()).isPresent()) {
            throw new TeacherAlreadyExistsException("Professor com email " + teacherRequestDTO.getEmail() + " já existe");
        }

        Teacher createdTeacher = mapper.map(teacherRequestDTO, Teacher.class);
        createdTeacher = teacherRepository.save(createdTeacher);
        createdTeacher.setPassword(new BCryptPasswordEncoder().encode(createdTeacher.getPassword()));

        return mapper.map(createdTeacher, TeacherResponseDTO.class);
    }

    /**
     * Retorna uma lista paginada de professores.
     *
     * @param pageable Objeto contendo informações de paginação.
     * @return Uma página de professores.
     */
    public Page<TeacherResponseDTO> findAllTeachers(Pageable pageable) {
        Page<Teacher> teachersPage = teacherRepository.findAll(pageable);
        List<TeacherResponseDTO> teachersListDTO = teachersPage.getContent().stream()
                .map(teacher -> mapper.map(teacher, TeacherResponseDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(teachersListDTO, pageable, teachersPage.getTotalElements());
    }

    public TeacherResponseDTO findTeacherById(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));
        return mapper.map(teacher, TeacherResponseDTO.class);
    }

    public TeacherResponseDTO findTeacherByEmail(String email){
        Teacher teacher = teacherRepository.findByEmail(email).orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));
        return mapper.map(teacher, TeacherResponseDTO.class);
    }

    public TeacherResponseDTO updateTeacher(Long id, TeacherUpdateRequestDTO teacherUpdateRequestDTO){
        Teacher teacher = teacherRepository.findById(id)
                                     .orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));

        teacher.setEmail(teacherUpdateRequestDTO.getEmail());
        teacher.setPhoneNumber(teacherUpdateRequestDTO.getPhoneNumber());
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return mapper.map(updatedTeacher, TeacherResponseDTO.class);
    }

    public void deleteTeacher(Long id){
        teacherRepository.deleteById(id);
    }

    public TeacherResponseDTO findByUsername(String name) {
        final var teacher = teacherRepository.findByUsername(name)
                .orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));
        return mapper.map(teacher, TeacherResponseDTO.class);
    }

    @Transactional
    public void addLessonToTeacher(Long teacherId, Long lessonId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                                           .orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));
        LessonResponseDTO lesson = lessonService.findLessonById(lessonId);
        Lesson lessonEntity = mapper.map(lesson, Lesson.class);
        teacher.addLessons(lessonEntity);
        teacherRepository.save(teacher);
    }

    @Transactional
    public void removeLessonFromTeacher(Long teacherId, Long lessonId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                                           .orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));
        LessonResponseDTO lesson = lessonService.findLessonById(lessonId);
        Lesson lessonEntity = mapper.map(lesson, Lesson.class);
        teacher.removeLessons(lessonEntity);
        teacherRepository.save(teacher);
    }

    
}