package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.materia.MateriaRequest;
import org.example.desafiodiogo.dto.materia.MateriaResponse;

import java.util.List;

public interface MateriaService {

    MateriaResponse criarMateria(final MateriaRequest request);

    List<MateriaResponse> listarMaterias();

    MateriaResponse obterMateriaPorId(final Long id);

    MateriaResponse atualizarMateria(final Long id, final MateriaRequest request);

    void deletarMateria(final Long id);

}

