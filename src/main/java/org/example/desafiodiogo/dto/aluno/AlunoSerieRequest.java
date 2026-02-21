package org.example.desafiodiogo.dto.aluno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlunoSerieRequest {

    private Long alunoId;
    private Long serieId;

}

