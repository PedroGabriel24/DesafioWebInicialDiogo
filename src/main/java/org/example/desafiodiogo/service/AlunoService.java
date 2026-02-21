package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.aluno.AlunoSerieRequest;
import org.example.desafiodiogo.dto.aluno.AlunoSerieResponse;

public interface AlunoService {

    AlunoSerieResponse adicionarAlunoASerie(AlunoSerieRequest request);

}
