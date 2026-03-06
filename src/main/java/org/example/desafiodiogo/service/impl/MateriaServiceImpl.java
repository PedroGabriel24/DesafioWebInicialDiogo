package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.materia.MateriaRequest;
import org.example.desafiodiogo.dto.materia.MateriaResponse;
import org.example.desafiodiogo.model.Materia;
import org.example.desafiodiogo.repository.MateriaRepository;
import org.example.desafiodiogo.service.MateriaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MateriaServiceImpl implements MateriaService {

    private final MateriaRepository materiaRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public MateriaResponse criarMateria(final MateriaRequest request) {
        Materia materia = Materia.builder()
                .nome(request.getNome())
                .status("ATIVO")
                .build();

        Materia savedMateria = materiaRepository.save(materia);
        return new MateriaResponse(savedMateria);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<MateriaResponse> listarMaterias() {
        return materiaRepository.findAll().stream()
                .map(MateriaResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public MateriaResponse obterMateriaPorId(final Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada com id: " + id));
        return new MateriaResponse(materia);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public MateriaResponse atualizarMateria(final Long id, final MateriaRequest request) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada com id: " + id));

        if (request.getNome() != null && !request.getNome().isEmpty()) {
            materia.setNome(request.getNome());
        }

        Materia updatedMateria = materiaRepository.save(materia);
        return new MateriaResponse(updatedMateria);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletarMateria(final Long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada com id: " + id));
        materia.setStatus("INATIVO");

        materiaRepository.save(materia);
    }

}

