package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;

import java.util.List;
import java.util.Map;

public interface ProfessorService {

    ProfessorMateriaSerieResponse adicionarProfessorAMateriaSerie(ProfessorMateriaSerieRequest request);

    Map<String, Object> getDashboardData();

    Map<String, Object> alunosPorMateria(final Long idMateria);

}
