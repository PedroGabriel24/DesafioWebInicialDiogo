package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.materia.MateriaComProfessoresResponse;
import org.example.desafiodiogo.dto.materia.ProfessorSeriePorMateriaResponse;
import org.example.desafiodiogo.dto.professor.MateriaPorProfessorResponse;
import org.example.desafiodiogo.dto.professor.ProfessorComMateriasResponse;
import org.example.desafiodiogo.model.ProfessorMateriaSerie;
import org.example.desafiodiogo.repository.ProfessorMateriaSerieRepository;
import org.example.desafiodiogo.service.ProfessorMateriaSerieListService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfessorMateriaSerieListServiceImpl implements ProfessorMateriaSerieListService {

    private final ProfessorMateriaSerieRepository professorMateriaSerieRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ProfessorComMateriasResponse obterProfessorComMateriasPorProfessorId(final Long professorId) {
        List<ProfessorMateriaSerie> materiasDoProf = professorMateriaSerieRepository.findAll().stream()
                .filter(pms -> pms.getProfessor().getId().equals(professorId))
                .collect(Collectors.toList());

        if (materiasDoProf.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma matéria encontrada para o professor com id: " + professorId);
        }

        ProfessorMateriaSerie primeiraMaterias = materiasDoProf.get(0);
        List<MateriaPorProfessorResponse> materias = materiasDoProf.stream()
                .map(pms -> MateriaPorProfessorResponse.builder()
                        .materiaId(pms.getMateria().getId())
                        .materiaNome(pms.getMateria().getNome())
                        .serieId(pms.getSerie().getId())
                        .serieNome(pms.getSerie().getNome())
                        .build())
                .collect(Collectors.toList());

        return ProfessorComMateriasResponse.builder()
                .professorId(primeiraMaterias.getProfessor().getId())
                .professorNome(primeiraMaterias.getProfessor().getNome())
                .materias(materias)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public MateriaComProfessoresResponse obterMateriaComProfessoresPorMateriaId(final Long materiaId) {
        List<ProfessorMateriaSerie> professoresMateria = professorMateriaSerieRepository.findAll().stream()
                .filter(pms -> pms.getMateria().getId().equals(materiaId))
                .collect(Collectors.toList());

        if (professoresMateria.isEmpty()) {
            throw new IllegalArgumentException("Nenhum professor encontrado para a matéria com id: " + materiaId);
        }

        ProfessorMateriaSerie primeiroRegistro = professoresMateria.get(0);
        List<ProfessorSeriePorMateriaResponse> professores = professoresMateria.stream()
                .map(pms -> ProfessorSeriePorMateriaResponse.builder()
                        .professorId(pms.getProfessor().getId())
                        .professorNome(pms.getProfessor().getNome())
                        .serieId(pms.getSerie().getId())
                        .serieNome(pms.getSerie().getNome())
                        .build())
                .collect(Collectors.toList());

        return MateriaComProfessoresResponse.builder()
                .materiaId(primeiroRegistro.getMateria().getId())
                .materiaNome(primeiroRegistro.getMateria().getNome())
                .professores(professores)
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletarProfessorMateriaSerie(final Long id) {
        ProfessorMateriaSerie professorMateriaSerie = professorMateriaSerieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Professor-Materia-Serie não encontrado com id: " + id));
        professorMateriaSerieRepository.delete(professorMateriaSerie);
    }

    @Override
    public List<ProfessorComMateriasResponse> listarProfessorMateriaSerie() {
        List<ProfessorMateriaSerie> materiasDoProf = professorMateriaSerieRepository.findAll();

        // Agrupar por professor para evitar duplicatas
        return materiasDoProf.stream()
                .collect(Collectors.groupingBy(pms -> pms.getProfessor().getId(),
                        Collectors.toList()))
                .values()
                .stream()
                .map(grupoProfessor -> {
                    ProfessorMateriaSerie primeiro = grupoProfessor.get(0);
                    List<MateriaPorProfessorResponse> materias = grupoProfessor.stream()
                            .map(pms -> MateriaPorProfessorResponse.builder()
                                    .id(pms.getId())
                                    .materiaId(pms.getMateria().getId())
                                    .materiaNome(pms.getMateria().getNome())
                                    .serieId(pms.getSerie().getId())
                                    .serieNome(pms.getSerie().getNome())
                                    .build())
                            .collect(Collectors.toList());

                    return ProfessorComMateriasResponse.builder()
                            .professorId(primeiro.getProfessor().getId())
                            .professorNome(primeiro.getProfessor().getNome())
                            .materias(materias)
                            .build();
                })
                .collect(Collectors.toList());
    }

}

