package org.example.desafiodiogo.service;

import java.util.List;

import org.example.desafiodiogo.dto.serie.SerieResponse;
import org.example.desafiodiogo.dto.serie.SerieRequest;

public interface SerieService {

    void deletarSerie(final Long id);

    SerieResponse atualizarSerie(final Long id, final SerieRequest request);

    SerieResponse obterSeriePorId(final Long id);

    List<SerieResponse> listarSeries();

    SerieResponse criarSerie(final SerieRequest request);
}

