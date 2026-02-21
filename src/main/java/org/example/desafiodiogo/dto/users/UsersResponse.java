package org.example.desafiodiogo.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.desafiodiogo.model.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponse {

    private Long id;
    private String nome;
    private String email;
    private String tipo;
    private String cpf;
    private LocalDate nascimento;
    private String telefone;
    private String status;
    private LocalDateTime cadastro;

    public UsersResponse(Users user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.tipo = user.getTipo() != null ? user.getTipo().name() : null;
        this.cpf = user.getCpf();
        this.nascimento = user.getNascimento();
        this.telefone = user.getTelefone();
        this.status = user.getStatus();
        this.cadastro = user.getCadastro();
    }
}

