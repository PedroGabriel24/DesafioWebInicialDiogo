package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.seriemateria.SerieMateriaRequest;
import org.example.desafiodiogo.dto.seriemateria.SerieMateriaResponse;
import org.example.desafiodiogo.model.Materia;
import org.example.desafiodiogo.model.Serie;
import org.example.desafiodiogo.model.SerieMateria;
import org.example.desafiodiogo.repository.MateriaRepository;
import org.example.desafiodiogo.repository.SerieRepository;
import org.example.desafiodiogo.repository.SerieMateriaRepository;
import org.example.desafiodiogo.service.SerieMateriaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SerieMateriaServiceImpl implements SerieMateriaService {

    private final SerieMateriaRepository serieMateriaRepository;
    private final SerieRepository serieRepository;
    private final MateriaRepository materiaRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SerieMateriaResponse criarSerieMateria(final SerieMateriaRequest request) {
        Serie serie = serieRepository.findById(request.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com id: " + request.getSerieId()));

        Materia materia = materiaRepository.findById(request.getMateriaId())
                .orElseThrow(() -> new RuntimeException("Matéria não encontrada com id: " + request.getMateriaId()));

        if (serieMateriaRepository.findBySerieIdAndMateriaId(serie.getId(), materia.getId()).isPresent()) {
            throw new RuntimeException("Associação Série-Matéria já existe");
        }

        SerieMateria serieMateria = SerieMateria.builder()
                .serie(serie)
                .materia(materia)
                .build();

        SerieMateria savedSerieMateria = serieMateriaRepository.save(serieMateria);
        return new SerieMateriaResponse(savedSerieMateria);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<SerieMateriaResponse> listarSerieMateriasPorSerie(final Long serieId) {
        return serieMateriaRepository.findBySerieId(serieId).stream()
                .map(SerieMateriaResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<SerieMateriaResponse> listarSerieMateriasPorMateria(final Long materiaId) {
        return serieMateriaRepository.findByMateriaId(materiaId).stream()
                .map(SerieMateriaResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletarSerieMateria(final Long id) {
        SerieMateria serieMateria = serieMateriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serie-Materia não encontrado com id: " + id));
        serieMateriaRepository.delete(serieMateria);
    }
}

