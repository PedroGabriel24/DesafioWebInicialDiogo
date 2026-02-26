package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.observacoes.ObservacoesRequest;
import org.example.desafiodiogo.dto.observacoes.ObservacoesResponse;
import org.example.desafiodiogo.model.Observacoes;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.ObservacoesRepository;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.AuthService;
import org.example.desafiodiogo.service.ObservacoesService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ObservacoesServiceImpl implements ObservacoesService {

    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final ObservacoesRepository observacoesRepository;

    @Override
    public void adicionarObservacao(ObservacoesRequest request){
        Users currentUser = authService.getCurrentUser();

        Users aluno = usersRepository.findById(request.getAlunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));

        if (aluno.getTipo() != ProfileEnum.ALUNO) {
            throw new IllegalArgumentException("O ID fornecido não corresponde a um aluno");
        }

        Observacoes observacoes = Observacoes.builder()
                .data(LocalDateTime.now())
                .aluno(aluno)
                .professor(currentUser)
                .mensagem(request.getMensagem())
                .status(request.getStatus())
                .build();

        observacoesRepository.save(observacoes);
    }

    @Override
    public List<ObservacoesResponse> listarObservacoes() {
        Users currentUser = authService.getCurrentUser();
        final boolean destinatario = currentUser.getTipo() == ProfileEnum.PROFESSOR;

        var observacoesList =  destinatario ?
                observacoesRepository.findObservacoesByProfessor(currentUser) :
                observacoesRepository.findObservacoesByAluno(currentUser);

        return observacoesList.stream()
                .map(observacoes -> new ObservacoesResponse(observacoes, destinatario))
                .toList();
    }
}
