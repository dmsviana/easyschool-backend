package br.edu.ifpb.ads.easyschool.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseRequestDTO {
    

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
