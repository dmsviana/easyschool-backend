package br.edu.ifpb.ads.easyschool.dtos.response;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import br.edu.ifpb.ads.easyschool.model.Course;
import br.edu.ifpb.ads.easyschool.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

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
