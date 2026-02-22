package org.example.desafiodiogo.dto.professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorComMateriasResponse {

    private Long professorId;
    private String professorNome;
    private List<MateriaPorProfessorResponse> materias;

}

