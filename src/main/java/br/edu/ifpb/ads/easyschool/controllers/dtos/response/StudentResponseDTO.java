package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import br.edu.ifpb.ads.easyschool.model.Fee;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentResponseDTO {
    

    private Long id;
    private String name;
    private String email;
    private String registration;
    private String phoneNumber;
    private List<Fee> fees;


}
