package br.edu.ifpb.ads.easyschool.controllers.dtos.request.lesson;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonUpdateRequestDTO {

    private String subject;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long courseId;
}