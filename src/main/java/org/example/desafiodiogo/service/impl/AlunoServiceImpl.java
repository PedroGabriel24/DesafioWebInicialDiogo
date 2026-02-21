package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;
import org.example.desafiodiogo.model.AlunosSerie;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.AlunosSerieRepository;
import org.example.desafiodiogo.repository.SerieRepository;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.AlunoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunosSerieRepository alunosSerieRepository;
    private final UsersRepository usersRepository;
    private final SerieRepository serieRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AlunoSerieResponse adicionarAlunoASerie(AlunoSerieRequest request) {
        var existing = alunosSerieRepository.findByAlunoIdAndSerieId(request.getAlunoId(), request.getSerieId());
        if (existing.isPresent()) {
            throw new RuntimeException("Aluno já existe nesta série");
        }

        Users aluno = usersRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado com id: " + request.getAlunoId()));

        var serie = serieRepository.findById(request.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com id: " + request.getSerieId()));

        AlunosSerie alunosSerie = AlunosSerie.builder()
                .aluno(aluno)
                .serie(serie)
                .build();

        AlunosSerie saved = alunosSerieRepository.save(alunosSerie);
        return new AlunoSerieResponse(saved);
    }
}