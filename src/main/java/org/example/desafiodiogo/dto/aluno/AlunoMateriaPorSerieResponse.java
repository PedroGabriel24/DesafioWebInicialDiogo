package org.example.desafiodiogo.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoMateriaPorSerieResponse {

    private Long alunoSerieId;
    private Long alunoId;
    private String alunoNome;
    private Long serieId;
    private String serieNome;
    private Boolean jaLancouNota;

}
