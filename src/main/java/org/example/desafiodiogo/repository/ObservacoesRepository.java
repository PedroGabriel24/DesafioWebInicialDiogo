package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.Observacoes;
import org.example.desafiodiogo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservacoesRepository extends JpaRepository<Observacoes, Long> {

    List<Observacoes> findObservacoesByAluno(Users aluno);

    List<Observacoes> findObservacoesByProfessor(Users professor);

}
