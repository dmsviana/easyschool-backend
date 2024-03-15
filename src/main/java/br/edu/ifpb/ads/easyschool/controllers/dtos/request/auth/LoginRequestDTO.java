package br.edu.ifpb.ads.easyschool.controllers.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDTO {

    @NotBlank
    private String username;
    
    @NotBlank
    private String password;

}
