package br.edu.ifpb.ads.easyschool.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerResponseDTO {
    
    private Long id;
    private String name;
    private String email;
    private String registration;
    private String phoneNumber;
}
