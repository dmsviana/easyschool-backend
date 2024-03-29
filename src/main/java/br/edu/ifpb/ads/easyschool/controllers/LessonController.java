package br.edu.ifpb.ads.easyschool.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.lesson.LessonPostRequestDTO;
import br.edu.ifpb.ads.easyschool.controllers.dtos.response.LessonResponseDTO;
import br.edu.ifpb.ads.easyschool.services.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(CREATED)
    @PostMapping("{teacherId}/{courseId}")
    public LessonResponseDTO createLesson(@RequestBody @Valid LessonPostRequestDTO lessonRequestDTO,
                                          @PathVariable Long teacherId,
                                          @PathVariable Long courseId) {
        return lessonService.createLesson(lessonRequestDTO, teacherId, courseId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(OK)
    @GetMapping("/get-all")
    public Page<LessonResponseDTO> findAllLessons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return lessonService.findAllLessons(pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @ResponseStatus(OK)
    @GetMapping("/{lessonId}")
    public LessonResponseDTO findLessonById(@PathVariable Long lessonId) {
        return lessonService.findLessonById(lessonId);
    }

}