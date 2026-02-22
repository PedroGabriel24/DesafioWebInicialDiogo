package org.example.desafiodiogo.dto.materia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MateriaComProfessoresResponse {

    private Long materiaId;
    private String materiaNome;
    private List<ProfessorSeriePorMateriaResponse> professores;

}

