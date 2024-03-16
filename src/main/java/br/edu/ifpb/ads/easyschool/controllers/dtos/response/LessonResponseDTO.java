package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import java.time.LocalTime;
import java.util.List;

import br.edu.ifpb.ads.easyschool.model.Teacher;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonResponseDTO {

    private Long id;

    private String subject;

    private LocalTime startTime;

    private LocalTime endTime;

    private CourseResponseDTO course;

    private List<Teacher> teachers;
    
}