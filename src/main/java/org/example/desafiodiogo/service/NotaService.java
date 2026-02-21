package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.aluno.BoletimResponse;
import org.example.desafiodiogo.dto.professor.LancarNotaRequest;
import org.example.desafiodiogo.model.Users;

public interface NotaService {
    void lancarNota(LancarNotaRequest request);

    BoletimResponse obterBoletimAluno(Users aluno);
}

