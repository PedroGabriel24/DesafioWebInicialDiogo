package org.example.desafiodiogo.service;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.UsersRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public Users loginUser(final String email) {
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> new RuntimeException("Senha ou Email incorretos."));
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
