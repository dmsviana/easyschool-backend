package br.edu.ifpb.ads.easyschool.controllers.dtos.request.course;

import java.time.DayOfWeek;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePostRequestDTO {

    @NotBlank(message = "Nome do curso é obrigatório.")
    private String name;

    @NotBlank(message = "Descrição do curso é obrigatória.")
    private String description;

    @NotNull(message = "Dias da semana são obrigatórios.")
    private List<DayOfWeek> daysOfWeek;

    @Positive(message = "Capacidade máxima deve ser um número positivo.")
    @NotNull(message = "Capacidade máxima é obrigatória.")
    private int maxCapacity;

}
