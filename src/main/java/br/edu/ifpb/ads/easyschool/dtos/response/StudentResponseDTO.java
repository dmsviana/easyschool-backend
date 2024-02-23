package br.edu.ifpb.ads.easyschool.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponseDTO {
    

    private Long id;
    private String name;
    private String email;
    private String registration;
    private String phoneNumber;


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
