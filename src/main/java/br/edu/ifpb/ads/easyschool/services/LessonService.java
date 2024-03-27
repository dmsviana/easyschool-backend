package br.edu.ifpb.ads.easyschool.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.lesson.LessonPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.LessonResponseDTO;
import br.edu.ifpb.ads.easyschool.exception.CourseNotFoundException;
import br.edu.ifpb.ads.easyschool.exception.LessonAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.LessonNotFoundException;
import br.edu.ifpb.ads.easyschool.exception.TeacherNotFoundException;
import br.edu.ifpb.ads.easyschool.model.Course;
import br.edu.ifpb.ads.easyschool.model.Lesson;
import br.edu.ifpb.ads.easyschool.model.Teacher;
import br.edu.ifpb.ads.easyschool.repositories.CourseRepository;
import br.edu.ifpb.ads.easyschool.repositories.LessonRepository;
import br.edu.ifpb.ads.easyschool.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ModelMapper mapper;

    @Transactional
    public LessonResponseDTO createLesson(LessonPostRequestDTO lessonRequestDTO, Long teacherId, Long courseId) {
        if (lessonRepository.findBySubject(lessonRequestDTO.getSubject()).isPresent()) {
            throw new LessonAlreadyExistsException("Aula com assunto " + lessonRequestDTO.getSubject() + " já existe");
        }

        Course course = courseRepository.findById(courseId)
                                        .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));

        Teacher teacher = teacherRepository.findById(teacherId)
                                           .orElseThrow(() -> new TeacherNotFoundException("Professor não encontrado."));

        Lesson createdLesson = mapper.map(lessonRequestDTO, Lesson.class);
        createdLesson.setCourse(course); 
        createdLesson.addTeacher(teacher);
        createdLesson = lessonRepository.save(createdLesson);

        return mapper.map(createdLesson, LessonResponseDTO.class);
    }

    public LessonResponseDTO findLessonById(Long id){
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException("Aula não encontrada."));
        return mapper.map(lesson, LessonResponseDTO.class);
    }

    /**
     * Retorna uma lista paginada de lições.
     *
     * @param pageable Objeto contendo informações de paginação.
     * @return Uma página de lições.
     */
    public Page<LessonResponseDTO> findAllLessons(Pageable pageable) {
        Page<Lesson> lessonsPage = lessonRepository.findAll(pageable);
        List<LessonResponseDTO> lessonsListDTO = lessonsPage.getContent().stream()
                .map(lesson -> mapper.map(lesson, LessonResponseDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(lessonsListDTO, pageable, lessonsPage.getTotalElements());
    }

    

}