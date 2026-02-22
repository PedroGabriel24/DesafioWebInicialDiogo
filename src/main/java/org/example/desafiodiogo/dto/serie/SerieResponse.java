package org.example.desafiodiogo.dto.serie;

import org.example.desafiodiogo.model.Serie;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SerieResponse {

    private String nome;
    private Long id;

    public SerieResponse(Serie serie) {
        this.nome = serie.getNome();
        this.id = serie.getId();
    }

}


