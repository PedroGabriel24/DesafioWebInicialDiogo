package org.example.desafiodiogo.dto.materia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.desafiodiogo.model.Materia;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MateriaResponse {

    private Long id;
    private String nome;

    public MateriaResponse(Materia materia) {
        this.id = materia.getId();
        this.nome = materia.getNome();
    }

}

