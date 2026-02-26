package org.example.desafiodiogo.model;

import jakarta.persistence.*;
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
@Table(name = "observacoes")
public class Observacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime data;
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Users aluno;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Users professor;
    private String status;

}
