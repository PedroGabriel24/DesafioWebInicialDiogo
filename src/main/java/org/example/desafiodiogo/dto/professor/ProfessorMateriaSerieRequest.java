package org.example.desafiodiogo.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorMateriaSerieRequest {

    private Long professorId;
    private Long materiaId;
    private Long serieId;

}

