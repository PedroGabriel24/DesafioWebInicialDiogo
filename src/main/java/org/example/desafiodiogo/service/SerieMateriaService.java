package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.seriemateria.SerieMateriaRequest;
import org.example.desafiodiogo.dto.seriemateria.SerieMateriaResponse;

import java.util.List;

public interface SerieMateriaService {

    SerieMateriaResponse criarSerieMateria(final SerieMateriaRequest request);

    List<SerieMateriaResponse> listarSerieMateriasPorSerie(final Long serieId);

    List<SerieMateriaResponse> listarSerieMateriasPorMateria(final Long materiaId);

    void deletarSerieMateria(final Long id);

}

