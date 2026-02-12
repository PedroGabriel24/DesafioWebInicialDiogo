package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.UsersRepository;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    public Users loginUser(final String email) {
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new RuntimeException("Senha ou Email incorretos."));
    }

    public void cadastro(final UsersRequest request) {
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

        usersRepository.save(user);
    }

    public ProfileJWTToken loadInfoUser(final Users user) {
        ProfileJWTToken token = new ProfileJWTToken(user);

        if (user.getTipo() != ProfileEnum.ADMIN) {
            var payload = usersRepository.loadInfoUser(user.getEmail(), user.getTipo().getProfileName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
            token.setPayload(payload);
        }

        return token;
    }

}
