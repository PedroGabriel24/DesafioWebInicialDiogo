package org.example.desafiodiogo.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateriaPorProfessorResponse {

    private Long materiaId;
    private String materiaNome;
    private Long serieId;
    private String serieNome;

}

