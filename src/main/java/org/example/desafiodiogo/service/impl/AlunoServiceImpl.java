package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.example.desafiodiogo.model.AlunosSerie;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.AlunosSerieRepository;
import org.example.desafiodiogo.repository.SerieRepository;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.AlunoService;
import org.example.desafiodiogo.service.AuthService;
import org.example.desafiodiogo.service.BoletimPdfService;
import org.example.desafiodiogo.service.NotaService;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunosSerieRepository alunosSerieRepository;
    private final UsersService usersService;
    private final SerieRepository serieRepository;
    private final NotaService notaService;
    private final AuthService authService;
    private final BoletimPdfService boletimPdfService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public AlunoSerieResponse adicionarAlunoASerie(AlunoSerieRequest request) {
        var existing = alunosSerieRepository.findByAlunoIdAndSerieId(request.getAlunoId(), request.getSerieId());
        if (existing.isPresent()) {
            throw new RuntimeException("Aluno já existe nesta série");
        }

        Users aluno = usersService.findUsersById(request.getAlunoId());

        var serie = serieRepository.findById(request.getSerieId())
                .orElseThrow(() -> new RuntimeException("Série não encontrada com id: " + request.getSerieId()));

        AlunosSerie alunosSerie = AlunosSerie.builder()
                .aluno(aluno)
                .serie(serie)
                .build();

        AlunosSerie saved = alunosSerieRepository.save(alunosSerie);
        return new AlunoSerieResponse(saved);
    }

    @Override
    public BoletimResponse obterBoletimAluno() {
        Users currentUser = authService.getCurrentUser();

        return notaService.obterBoletimAluno(currentUser);
    }

    @Override
    @PreAuthorize("hasRole('ALUNO')")
    public byte[] gerarBoletimPdf() throws IOException {
        BoletimResponse boletim = obterBoletimAluno();
        return boletimPdfService.gerarPdfBoletim(boletim);
    }

    @Override
    public List<Map<String, Object>> listarMaterias() {
        Users currentUser = authService.getCurrentUser();

        return usersService.loadInfoUser(currentUser.getEmail(), currentUser.getTipo());
    }
}