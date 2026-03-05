package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.dto.users.UsersResponse;
import org.example.desafiodiogo.dto.users.UsersUpdateRequest;

import java.util.List;

public interface AdminService {

    UsersResponse cadastro(final UsersRequest request);

    List<UsersResponse> listarUsuarios();

    UsersResponse obterUsuarioPorId(final Long id);

    UsersResponse atualizarUsuario(final Long id, final UsersUpdateRequest request);

}
