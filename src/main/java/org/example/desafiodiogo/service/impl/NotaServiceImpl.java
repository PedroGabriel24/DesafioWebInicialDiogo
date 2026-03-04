package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.example.desafiodiogo.dto.professor.LancarNotaRequest;
import org.example.desafiodiogo.model.*;
import org.example.desafiodiogo.repository.AlunosSerieRepository;
import org.example.desafiodiogo.repository.MateriaRepository;
import org.example.desafiodiogo.repository.NotaRepository;
import org.example.desafiodiogo.repository.PeriodoRepository;
import org.example.desafiodiogo.service.AuthService;
import org.example.desafiodiogo.service.NotaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final AlunosSerieRepository alunosSerieRepository;
    private final MateriaRepository materiaRepository;
    private final PeriodoRepository periodoRepository;

    @Override
    @PreAuthorize("hasRole('PROFESSOR')")
    public void lancarNota(LancarNotaRequest request) {
        AlunosSerie als = alunosSerieRepository.findById(request.getAlunoSerieId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno-Série não encontrado"));

        Materia materia = materiaRepository.findById(request.getMateriaId())
                .orElseThrow(() -> new IllegalArgumentException("Matéria não encontrada"));

        Periodo periodo = periodoRepository.findById(request.getPeriodoId())
                .orElseThrow(() -> new IllegalArgumentException("Período não encontrado"));

        Nota nota = Nota.builder()
                .alunoSerie(als)
                .materia(materia)
                .periodo(periodo)
                .nota(request.getNota())
                .cadastro(LocalDateTime.now())
                .build();

        notaRepository.save(nota);
    }

    @Override
    public void alterarNota(LancarNotaRequest request) {
        Nota nota = notaRepository.findNotaByAlunoSerie_IdAndPeriodo_Id(
                request.getAlunoSerieId(),
                request.getPeriodoId())
                .orElseThrow(() -> new IllegalArgumentException("Nota não encontrada para os parâmetros fornecidos"));

        nota.setNota(request.getNota());
        notaRepository.save(nota);
    }

    @Override
    @PreAuthorize("hasRole('ALUNO')")
    public BoletimResponse obterBoletimAluno(Users aluno) {
        List<Nota> notas = notaRepository.findByAlunoIdOrderByMateriaPeriodo(aluno.getId());

        Map<Long, BoletimResponse.DisciplinaNotas> mapa = new LinkedHashMap<>();

        for (Nota n : notas) {
            Long materiaId = n.getMateria().getId();
            BoletimResponse.DisciplinaNotas disc = mapa.computeIfAbsent(materiaId, id -> BoletimResponse.DisciplinaNotas.builder()
                    .materiaId(id)
                    .materiaNome(n.getMateria().getNome())
                    .notas(new ArrayList<>())
                    .build());

            BoletimResponse.PeriodoNota pn = BoletimResponse.PeriodoNota.builder()
                    .periodoId(n.getPeriodo().getId())
                    .periodoNome(n.getPeriodo().getNome())
                    .nota(n.getNota())
                    .build();
            disc.getNotas().add(pn);
        }

        List<BoletimResponse.DisciplinaNotas> disciplinas = new ArrayList<>(mapa.values());

        return BoletimResponse.builder()
                .alunoId(aluno.getId())
                .alunoNome(aluno.getNome())
                .disciplinas(disciplinas)
                .build();
    }
}
