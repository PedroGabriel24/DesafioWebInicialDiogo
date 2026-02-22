package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;
import org.example.desafiodiogo.dto.aluno.BoletimResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AlunoService {

    AlunoSerieResponse adicionarAlunoASerie(AlunoSerieRequest request);

    BoletimResponse obterBoletimAluno();

    byte[] gerarBoletimPdf() throws IOException;

    List<Map<String, Object>> listarMaterias();

}
