package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import java.math.BigDecimal;
import java.util.List;

import br.edu.ifpb.ads.easyschool.model.Lesson;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherResponseDTO {
    

    private Long id;
    private String name;
    private String email;
    private String registration;
    private String phoneNumber;
    private BigDecimal salary;
    private List<Lesson> lessons;
}
