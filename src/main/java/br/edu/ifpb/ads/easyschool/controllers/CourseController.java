package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.course.CoursePostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.CourseResponseDTO;
import br.edu.ifpb.ads.easyschool.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/courses")
@RequiredArgsConstructor
public class CourseController {
    

    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping
    public CourseResponseDTO createCourse(@RequestBody @Valid CoursePostRequestDTO courseRequestDTO){
        CourseResponseDTO course = courseService.createCourse(courseRequestDTO);
        return course;
    }
    
    @ResponseStatus(OK)
    @GetMapping("/get-all")
    public Page<CourseResponseDTO> findAllCourses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return courseService.findAllCourses(pageable);
    }

    @ResponseStatus(OK)
    @GetMapping("/{courseId}")
    public CourseResponseDTO findCourseById(@PathVariable Long courseId){
        CourseResponseDTO course = courseService.findCourseById(courseId);
        return course;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @PostMapping("/{courseId}/associate/{studentId}")
    public void associateStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.associateStudentToCourse(studentId, courseId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{courseId}/desassociate/{studentId}")
    public void desassociateStudentToCourse(@PathVariable Long courseId, @PathVariable Long studentId){
        courseService.desassociateStudentToCourse(studentId, courseId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
    }

    
}
