package br.edu.ifpb.ads.easyschool.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class StudentRequestDTO {
    

    @NotBlank(message = "O campo nome não pode ser vazio.")
    private String name;

    @NotBlank(message = "O campo email não pode ser vazio.")
    @Email(message = "O campo email deve ser um email válido.")
    private String email;

    @NotBlank(message = "O campo senha não pode ser vazio.")
    @Size(min = 8, max = 16, message = "A senha deve ter entre 8 e 16 caracteres.")
    private String password;

    @NotBlank(message = "O campo username não pode ser vazio.")
    private String username;

    @NotBlank(message = "O campo matrícula não pode ser vazio.")
    private String registration;
    
    @NotBlank(message = "O campo telefone não pode ser vazio.")
    @Size(min = 11, max = 11, message = "O telefone deve ter 11 dígitos.")
    private String phoneNumber;

    @NonNull
    private boolean admin;

}
