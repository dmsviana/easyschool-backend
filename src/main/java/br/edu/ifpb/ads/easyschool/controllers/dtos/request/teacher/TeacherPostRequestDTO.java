package br.edu.ifpb.ads.easyschool.controllers.dtos.request.teacher;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherPostRequestDTO {

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

    @DecimalMin(value = "0.0", message = "O salário deve ser maior que 0.")
    @NotNull(message = "O campo salário não pode ser vazio.")
    private BigDecimal salary;
    
}
