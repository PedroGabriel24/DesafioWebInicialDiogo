package org.example.desafiodiogo.dto.seriemateria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.desafiodiogo.model.SerieMateria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerieMateriaResponse {

    private Long id;
    private Long serieId;
    private String serieNome;
    private Long materiaId;
    private String materiaNome;

    public SerieMateriaResponse(SerieMateria serieMateria) {
        this.id = serieMateria.getId();
        this.serieId = serieMateria.getSerie().getId();
        this.serieNome = serieMateria.getSerie().getNome();
        this.materiaId = serieMateria.getMateria().getId();
        this.materiaNome = serieMateria.getMateria().getNome();
    }

}

