package br.edu.ifpb.ads.easyschool.controllers.dtos.request;

import java.time.DayOfWeek;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateRequestDTO {

    @NotNull(message = "Dias da semana são obrigatórios.")
    private List<DayOfWeek> daysOfWeek;

    @Positive(message = "Capacidade máxima deve ser um número positivo.")
    @NotNull(message = "Capacidade máxima é obrigatória.")
    private int maxCapacity;

}
