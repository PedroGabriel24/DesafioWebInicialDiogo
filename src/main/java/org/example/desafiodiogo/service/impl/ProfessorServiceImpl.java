package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieRequest;
import org.example.desafiodiogo.dto.professor.ProfessorMateriaSerieResponse;
import org.example.desafiodiogo.model.Materia;
import org.example.desafiodiogo.model.ProfessorMateriaSerie;
import org.example.desafiodiogo.model.Serie;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.*;
import org.example.desafiodiogo.service.AuthService;
import org.example.desafiodiogo.service.ProfessorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorMateriaSerieRepository professorMateriaSerieRepository;
    private final UsersRepository usersRepository;
    private final MateriaRepository materiaRepository;
    private final SerieRepository serieRepository;
    private final AuthService authService;
    private final ReportRepository reportRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessorMateriaSerieResponse adicionarProfessorAMateriaSerie(ProfessorMateriaSerieRequest request) {
        var existing = professorMateriaSerieRepository.findByProfessorIdAndMateriaIdAndSerieId(
                request.getProfessorId(), request.getMateriaId(), request.getSerieId());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Professor já está associado a esta matéria e série");
        }

        Users professor = usersRepository.findById(request.getProfessorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado com id: " + request.getProfessorId()));

        Materia materia = materiaRepository.findById(request.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada com id: " + request.getMateriaId()));

        Serie serie = serieRepository.findById(request.getSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Série não encontrada com id: " + request.getSerieId()));

        ProfessorMateriaSerie professorMateriaSerie = ProfessorMateriaSerie.builder()
                .professor(professor)
                .materia(materia)
                .serie(serie)
                .build();

        ProfessorMateriaSerie saved = professorMateriaSerieRepository.save(professorMateriaSerie);
        return new ProfessorMateriaSerieResponse(saved);
    }

    @Override
    @PreAuthorize("hasRole('PROFESSOR')")
    public Map<String, Object> getDashboardData(Optional<Long> disciplinaId, Optional<Long> turmaId) {
        Users user = authService.getCurrentUser();

        List<Object[]> rows;

        // Seleciona o método correto do repository baseado nos filtros fornecidos
        if (disciplinaId.isPresent() && turmaId.isPresent()) {
            rows = reportRepository.getDashboardPorDisciplinaETurma(user.getId(), disciplinaId.get(), turmaId.get());
        } else if (disciplinaId.isPresent()) {
            rows = reportRepository.getDashboardPorDisciplina(user.getId(), disciplinaId.get());
        } else if (turmaId.isPresent()) {
            rows = reportRepository.getDashboardPorTurma(user.getId(), turmaId.get());
        } else {
            rows = reportRepository.getDashboard(user.getId());
        }

        List<Map<String, Object>> materias = new ArrayList<>();
        for (Object[] r : rows) {
            String nome = r[0] != null ? r[0].toString() : null;
            Number totalAlunosNum = r[1] != null ? (Number) r[1] : 0;
            Object mediaObj = r[2];
            Number pendenciasNum = r[3] != null ? (Number) r[3] : 0;


            Double mediaTurma = null;
            if (mediaObj instanceof Number) {
                mediaTurma = ((Number) mediaObj).doubleValue();
            }

            Map<String, Object> m = new HashMap<>();
            m.put("nome", nome);
            m.put("totalAlunos", totalAlunosNum.intValue());
            m.put("mediaTurma", mediaTurma);
            m.put("pendencias", pendenciasNum.intValue());

            materias.add(m);
        }

        return Map.of("materias", materias);
    }

    @Override
    @PreAuthorize("hasRole('PROFESSOR')")
    public Map<String, Object> alunosPorMateria(final Long idMateria) {
        Users user = authService.getCurrentUser();

        long count = professorMateriaSerieRepository.countByProfessorIdAndMateriaId(user.getId(), idMateria);
        if (count == 0L) {
            throw new IllegalArgumentException("Professor não responsável por esta matéria");
        }

        List<Object[]> rows = reportRepository.getAlunosPorMateria(user.getId(), idMateria);

        List<Map<String, Object>> alunos = new ArrayList<>();
        for (Object[] r : rows) {
            Number alunoIdNum = r[1] != null ? (Number) r[1] : 0;
            String nome = r[2] != null ? r[2].toString() : null;
            String serie = r[3] != null ? r[3].toString() : null;
            Number jaLancouNum = r[4] != null ? (Number) r[4] : 0;

            Map<String, Object> m = new HashMap<>();
            m.put("id", alunoIdNum.longValue());
            m.put("nome", nome);
            m.put("serie", serie);
            m.put("jaLancouNota", jaLancouNum.intValue() == 1);

            alunos.add(m);
        }

        return Map.of("alunos", alunos);
    }
}

