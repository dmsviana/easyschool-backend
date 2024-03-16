package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.teacher.TeacherPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.request.teacher.TeacherUpdateRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.TeacherResponseDTO;
import br.edu.ifpb.ads.easyschool.services.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping
    public TeacherResponseDTO createTeacher(@RequestBody @Valid TeacherPostRequestDTO teacherRequestDTO) {
        return teacherService.createTeacher(teacherRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(OK)
    @GetMapping("/get-all")
    public List<TeacherResponseDTO> findAllTeachers() {
        return teacherService.findAllTeachers();
    }

    @PreAuthorize("hasRole('ADMIN')")

    @ResponseStatus(OK)
    @GetMapping("/{teacherId}")
    public TeacherResponseDTO findTeacherById(@PathVariable Long teacherId) {
        return teacherService.findTeacherById(teacherId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(OK)
    @PutMapping("/{teacherId}")
    public TeacherResponseDTO updateTeacher(@PathVariable Long teacherId, @RequestBody @Valid TeacherUpdateRequestDTO teacherUpdateRequestDTO) {
        return teacherService.updateTeacher(teacherId, teacherUpdateRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{teacherId}")
    public void deleteTeacher(@PathVariable Long teacherId) {
        teacherService.deleteTeacher(teacherId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(OK)
    @GetMapping("/username/{username}")
    public TeacherResponseDTO findByUsername(@PathVariable String username) {
        return teacherService.findByUsername(username);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @PostMapping("/{teacherId}/lessons/{lessonId}")
    public void addLessonToTeacher(@PathVariable Long teacherId, @PathVariable Long lessonId) {
        teacherService.addLessonToTeacher(teacherId, lessonId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{teacherId}/lessons/{lessonId}")
    public void removeLessonFromTeacher(@PathVariable Long teacherId, @PathVariable Long lessonId) {
        teacherService.removeLessonFromTeacher(teacherId, lessonId);
    }
}
