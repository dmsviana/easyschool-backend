package br.edu.ifpb.ads.easyschool.dtos.request;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseRequestDTO {
    

    @NotBlank(message = "Nome do curso é obrigatório.")
    private String name;

    @NotBlank(message = "Descrição do curso é obrigatória.")
    private String description;

    @NotNull(message = "Dias da semana são obrigatórios.")
    private List<DayOfWeek> daysOfWeek;

    @NotNull(message = "Horário de início é obrigatório.")
    private LocalTime startTime;

    @Positive(message = "Capacidade máxima deve ser um número positivo.")
    @NotNull(message = "Capacidade máxima é obrigatória.")
    private int maxCapacity;
    


}
