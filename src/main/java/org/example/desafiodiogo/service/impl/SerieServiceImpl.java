package org.example.desafiodiogo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.example.desafiodiogo.service.SerieService;
import org.example.desafiodiogo.repository.SerieRepository;
import org.example.desafiodiogo.model.Serie;
import org.example.desafiodiogo.dto.serie.SerieResponse;
import org.example.desafiodiogo.dto.serie.SerieRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SerieServiceImpl implements SerieService {

    private final SerieRepository serieRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SerieResponse criarSerie(final SerieRequest request) {

        Serie serie = Serie.builder()
                .nome(request.getNome())
                .build();

        Serie savedSerie = serieRepository.save(serie);

        return new SerieResponse(savedSerie);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<SerieResponse> listarSeries() {

        return serieRepository.findAll()
                .stream()
                .map(SerieResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SerieResponse obterSeriePorId(final Long id) {

        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Série não encontrada com id: " + id));

        return new SerieResponse(serie);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SerieResponse atualizarSerie(final Long id, final SerieRequest request) {

        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Série não encontrada com id: " + id));

        if (request.getNome() != null && !request.getNome().isEmpty()) {
            serie.setNome(request.getNome());
        }

        Serie updatedSerie = serieRepository.save(serie);

        return new SerieResponse(updatedSerie);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletarSerie(final Long id) {

        Serie serie = serieRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Série não encontrada com id: " + id));

        serieRepository.delete(serie);
    }
}