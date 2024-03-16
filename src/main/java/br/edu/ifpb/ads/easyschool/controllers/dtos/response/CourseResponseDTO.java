package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import br.edu.ifpb.ads.easyschool.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@Data
@NoArgsConstructor
public class CourseResponseDTO {
    

    private Long id;
    private String name;
    private String description;
    private List<DayOfWeek> daysOfWeek;
    private int maxCapacity;
    private List<Student> students;


}
