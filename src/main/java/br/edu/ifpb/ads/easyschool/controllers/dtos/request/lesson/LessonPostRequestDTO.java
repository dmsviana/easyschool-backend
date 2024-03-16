package br.edu.ifpb.ads.easyschool.controllers.dtos.request.lesson;

import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonPostRequestDTO {

    @NotBlank(message = "O campo assunto não pode ser vazio.")
    private String subject;

    @NotNull(message = "O campo horário de início não pode ser vazio.")
    private LocalTime startTime;
}
