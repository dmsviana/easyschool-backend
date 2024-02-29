package br.edu.ifpb.ads.easyschool.services;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.ads.easyschool.dtos.response.CourseResponseDTO;
import br.edu.ifpb.ads.easyschool.model.Course;
import br.edu.ifpb.ads.easyschool.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.edu.ifpb.ads.easyschool.dtos.request.CourseRequestDTO;
import br.edu.ifpb.ads.easyschool.exception.StudentNotFoundException;
import br.edu.ifpb.ads.easyschool.exception.CourseAlreadyExistsException;
import br.edu.ifpb.ads.easyschool.exception.CourseNotFoundException;
import br.edu.ifpb.ads.easyschool.repositories.StudentRepository;
import br.edu.ifpb.ads.easyschool.repositories.CourseRepository;
import jakarta.transaction.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public CourseResponseDTO createCourse(CourseRequestDTO courseRequest) {
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

    public List<CourseResponseDTO> findAllCourses() {
        List<Course> coursesList = courseRepository.findAll();

        List<CourseResponseDTO> coursesListDTO = new ArrayList<>();

        for (Course course : coursesList) {
            coursesListDTO.add(mapper.map(course, CourseResponseDTO.class));
        }

        return coursesListDTO;
    }

    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO cursoRequest) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));

        course.setName(cursoRequest.getName());
        course.setDescription(cursoRequest.getDescription());
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

        course.addStudent(student);
        courseRepository.save(course);
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
