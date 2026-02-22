package org.example.desafiodiogo.dto.materia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorSeriePorMateriaResponse {

    private Long professorId;
    private String professorNome;
    private Long serieId;
    private String serieNome;

}

