package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.observacoes.ObservacoesRequest;
import org.example.desafiodiogo.dto.observacoes.ObservacoesResponse;

import java.util.List;

public interface ObservacoesService {

    void adicionarObservacao(final ObservacoesRequest request);

    List<ObservacoesResponse> listarObservacoes();

}
