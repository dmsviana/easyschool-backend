package br.edu.ifpb.ads.easyschool.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.course.CoursePostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.course.CourseUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.CourseResponseDTO;
import br.edu.ifpb.ads.easyschool.exception.CourseAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.CourseNotFoundException;
import br.edu.ifpb.ads.easyschool.exception.MaximumCapacityExceededException;
import br.edu.ifpb.ads.easyschool.exception.StudentNotFoundException;
import br.edu.ifpb.ads.easyschool.model.Course;
import br.edu.ifpb.ads.easyschool.model.Student;
import br.edu.ifpb.ads.easyschool.producers.CourseProducer;
import br.edu.ifpb.ads.easyschool.repositories.CourseRepository;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseProducer courseProducer;

    private final ModelMapper mapper;

    public CourseResponseDTO createCourse(CoursePostRequestDTO courseRequest) {
        if (courseRepository.findByName(courseRequest.getName()).isPresent()) {
            throw new CourseAlreadyExistsException("Curso com nome " + courseRequest.getName() + " já existe");
        }

        Course courseCreated = mapper.map(courseRequest, Course.class);
        courseCreated = courseRepository.save(courseCreated);

        return mapper.map(courseCreated, CourseResponseDTO.class);

    }

    public CourseResponseDTO findCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));
        return mapper.map(course, CourseResponseDTO.class);
    }

    /**
     * Retorna uma lista paginada de cursos.
     *
     * @param pageable Objeto contendo informações de paginação.
     * @return Uma página de cursos.
     */
    public Page<CourseResponseDTO> findAllCourses(Pageable pageable) {
        Page<Course> coursesPage = courseRepository.findAll(pageable);
        List<CourseResponseDTO> coursesListDTO = coursesPage.getContent().stream()
                .map(course -> mapper.map(course, CourseResponseDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(coursesListDTO, pageable, coursesPage.getTotalElements());
    }

    public CourseResponseDTO updateCourse(Long id, CourseUpdateRequestDTO cursoRequest) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));

        course.setDaysOfWeek(cursoRequest.getDaysOfWeek());
        course.setMaxCapacity(cursoRequest.getMaxCapacity());

        Course updatedCourse = courseRepository.save(course);
        return mapper.map(updatedCourse, CourseResponseDTO.class);
    }

    @Transactional
    public void associateStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));

        if (course.getStudents().size() >= course.getMaxCapacity()) {
            throw new MaximumCapacityExceededException("Capacidade máxima do curso atingida.");
        }

        course.addStudent(student);
        courseRepository.save(course);
        courseProducer.publishCourseEnrollmentEmail(student, course);
    }

    @Transactional
    public void desassociateStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Aluno não encontrado."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));

        course.removeStudent(student);
        courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

}
