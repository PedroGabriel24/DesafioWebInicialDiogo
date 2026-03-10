package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProfessorService {

    ProfessorMateriaSerieResponse adicionarProfessorAMateriaSerie(ProfessorMateriaSerieRequest request);

    Map<String, Object> getDashboardData(Optional<Long> disciplinaId, Optional<Long> turmaId);

    Map<String, Object> alunosPorMateria(final Long idMateria);

}
