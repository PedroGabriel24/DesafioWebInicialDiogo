package org.example.desafiodiogo.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersRequest {

    @NotBlank(message = "Name cannot be blank.")
    private String nome;

    @CPF(message = "Invalid CPF format.")
    private String cpf;

    @NotBlank
    private String tipo;

    @NotBlank
    private String telefone;

    @NotBlank
    private String nascimento;

}
