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


    /*
    public static CourseResponseDTO convertToDTO(Course course){
        var cursoDto = new CourseResponseDTO();

        cursoDto.setId(course.getId());
        cursoDto.setName(course.getName());
        cursoDto.setDescription(course.getDescription());
        cursoDto.setDaysOfWeek(course.getDaysOfWeek());
        cursoDto.setStartTime(course.getStartTime());
        cursoDto.setEndTime(course.getEndTime());
        cursoDto.setMaxCapacity(course.getMaxCapacity());
        cursoDto.setStudents(course.getStudents());
        return cursoDto;
    } */

}
