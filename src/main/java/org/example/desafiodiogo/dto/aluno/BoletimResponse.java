package org.example.desafiodiogo.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoletimResponse {
    private Long alunoId;
    private String alunoNome;
    private List<DisciplinaNotas> disciplinas;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DisciplinaNotas {
        private Long materiaId;
        private String materiaNome;
        private List<PeriodoNota> notas;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeriodoNota {
        private Long periodoId;
        private String periodoNome;
        private BigDecimal nota;
    }
}

