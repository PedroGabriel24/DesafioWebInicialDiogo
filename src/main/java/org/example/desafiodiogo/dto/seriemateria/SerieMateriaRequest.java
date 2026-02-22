package org.example.desafiodiogo.dto.seriemateria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SerieMateriaRequest {

    private Long serieId;
    private Long materiaId;

}

