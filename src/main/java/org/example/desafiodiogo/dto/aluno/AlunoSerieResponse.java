package org.example.desafiodiogo.dto.aluno;

import org.example.desafiodiogo.model.AlunosSerie;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlunoSerieResponse {

    private String serieNome;
    private Long serieId;
    private String alunoNome;
    private Long alunoId;
    private Long id;

    public AlunoSerieResponse(AlunosSerie alunosSerie) {
        this.serieNome =alunosSerie.getSerie().getNome();
        this.serieId =alunosSerie.getSerie().getId();
        this.alunoNome =alunosSerie.getAluno().getNome();
        this.alunoId =alunosSerie.getAluno().getId();
        this.id =alunosSerie.getId();
    }
}


