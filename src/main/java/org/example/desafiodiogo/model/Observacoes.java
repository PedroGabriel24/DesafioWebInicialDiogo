package org.example.desafiodiogo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notas")
public class Observacoes {

    @Id
    private Long id;
    private LocalDateTime data;
    private String mensagem;
    private Long alunoId;
    private Long professorId;
    private String status;

}
