package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.dto.users.UsersResponse;
import org.example.desafiodiogo.dto.users.UsersUpdateRequest;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.AdminService;
import org.example.desafiodiogo.service.AuthService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UsersRepository usersRepository;
    private final AuthService authService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UsersResponse cadastro(final UsersRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado.");
        }
        Users user = Users.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(AuthServiceImpl.encodePassword(request.getSenha()))
                .cpf(request.getCpf())
                .cadastro(LocalDateTime.now())
                .nascimento(LocalDate.parse(request.getNascimento()))
                .tipo(ProfileEnum.valueOf(request.getTipo()))
                .telefone(request.getTelefone())
                .status("A")
                .build();

        Users savedUser = usersRepository.save(user);
        return new UsersResponse(savedUser);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsersResponse> listarUsuarios() {
        return usersRepository.findAll().stream()
                .map(UsersResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UsersResponse obterUsuarioPorId(final Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
        return new UsersResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UsersResponse atualizarUsuario(final Long id, final UsersUpdateRequest request) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        if (request.getNome() != null && !request.getNome().isEmpty()) {
            user.setNome(request.getNome());
        }

        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!user.getEmail().equals(request.getEmail()) && usersRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email já cadastrado.");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getSenha() != null && !request.getSenha().isEmpty()) {
            user.setSenha(AuthServiceImpl.encodePassword(request.getSenha()));
        }

        if (request.getCpf() != null && !request.getCpf().isEmpty()) {
            user.setCpf(request.getCpf());
        }

        if (request.getTipo() != null && !request.getTipo().isEmpty()) {
            user.setTipo(ProfileEnum.valueOf(request.getTipo()));
        }

        if (request.getTelefone() != null && !request.getTelefone().isEmpty()) {
            user.setTelefone(request.getTelefone());
        }

        if (request.getNascimento() != null && !request.getNascimento().isEmpty()) {
            user.setNascimento(LocalDate.parse(request.getNascimento()));
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            user.setStatus(request.getStatus());
        }

        Users updatedUser = usersRepository.save(user);
        return new UsersResponse(updatedUser);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deletarUsuario(final Long id) {
        usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        Users currentUser = authService.getCurrentUser();

        if (currentUser != null && currentUser.getId().equals(id)) {
            throw new RuntimeException("Você não pode deletar sua própria conta");
        }

        usersRepository.deleteById(id);
    }

}


