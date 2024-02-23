package br.edu.ifpb.ads.easyschool.controllers;

import java.util.List;

import br.edu.ifpb.ads.easyschool.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.dtos.request.CourseRequestDTO;
import br.edu.ifpb.ads.easyschool.dtos.response.CourseResponseDTO;
import br.edu.ifpb.ads.easyschool.services.CourseService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    

    private final CourseService courseService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }


    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO){
        CourseResponseDTO course = courseService.createCourse(courseRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CourseResponseDTO>> findAllCourses(){
        List<CourseResponseDTO> coursesList = courseService.findAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(coursesList);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> findCourseById(@PathVariable Long courseId){
        CourseResponseDTO course = courseService.findCourseById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    /* 
    @GetMapping("/{cursoId}/alunos")
    public ResponseEntity<List<StudentResponseDTO>> buscarAlunosPorCurso(@PathVariable Long cursoId){
        CourseResponseDTO curso = cursoService.buscarCursoPorId(cursoId);
    
        List<StudentResponseDTO> listaResponse = alunoService.buscarTodosAlunos();
        for (StudentResponseDTO aluno : listaResponse) {
            listaResponse.add(aluno);
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaResponse);
    } */

    @PostMapping("/{courseId}/associate/{studentId}")
    public ResponseEntity<Void> associateStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.associateStudentToCourse(studentId, courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{courseId}/desassociate/{studentId}")
    public ResponseEntity<Void> desassociateStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId){
        courseService.desassociateStudentToCourse(studentId, courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    
}
