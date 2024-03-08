package br.edu.ifpb.ads.easyschool.controllers.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateRequestDTO {

    @NotBlank(message = "O campo email não pode ser vazio.")
    @Email(message = "O campo email deve ser um email válido.")
    private String email;

    @NotBlank(message = "O campo telefone não pode ser vazio.")
    @Size(min = 11, max = 11, message = "O telefone deve ter 11 dígitos.")
    private String phoneNumber;

    @Size(min = 8, max = 16, message = "A senha deve ter entre 8 e 16 caracteres.")
    private String password;

}