package br.edu.ifpb.ads.easyschool.dtos.response;

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
    private boolean admin;


    /*
    public static StudentResponseDTO convertToDTO(Student student){
        var alunoDto = new StudentResponseDTO();

        alunoDto.setId(student.getId());
        alunoDto.setNome(student.getName());
        alunoDto.setEmail(student.getEmail());
        alunoDto.setMatricula(student.getRegistration());
        alunoDto.setTelefone(student.getPhoneNumber());
        return alunoDto;
    } */

}
