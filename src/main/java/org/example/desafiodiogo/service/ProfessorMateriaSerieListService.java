package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.materia.MateriaComProfessoresResponse;
import org.example.desafiodiogo.dto.professor.ProfessorComMateriasResponse;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;

import java.util.List;

public interface ProfessorMateriaSerieListService {

    ProfessorComMateriasResponse obterProfessorComMateriasPorProfessorId(final Long professorId);

    MateriaComProfessoresResponse obterMateriaComProfessoresPorMateriaId(final Long materiaId);

    void deletarProfessorMateriaSerie(final Long id);

    List<ProfessorComMateriasResponse> listarProfessorMateriaSerie();

}

