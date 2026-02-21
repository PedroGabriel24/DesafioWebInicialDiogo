package org.example.desafiodiogo.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.desafiodiogo.model.ProfessorMateriaSerie;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorMateriaSerieResponse {

    private Long id;
    private Long professorId;
    private String professorNome;
    private Long materiaId;
    private String materiaNome;
    private Long serieId;
    private String serieNome;

    public ProfessorMateriaSerieResponse(ProfessorMateriaSerie professorMateriaSerie) {
        this.id = professorMateriaSerie.getId();
        this.professorId = professorMateriaSerie.getProfessor().getId();
        this.professorNome = professorMateriaSerie.getProfessor().getNome();
        this.materiaId = professorMateriaSerie.getMateria().getId();
        this.materiaNome = professorMateriaSerie.getMateria().getNome();
        this.serieId = professorMateriaSerie.getSerie().getId();
        this.serieNome = professorMateriaSerie.getSerie().getNome();
    }

}

