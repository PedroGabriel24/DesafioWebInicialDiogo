package org.example.desafiodiogo.service;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.config.JwtProvider;
import org.example.desafiodiogo.dto.auth.AuthRequestParams;
import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtProvider jwtHelper;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;

    public String loginUser(final AuthRequestParams params) {
        Users infoLogin = usersService.loginUser(params.getEmail());
        if (!passwordEncoder.matches(params.getPassword(), infoLogin.getPassword())) {
            throw new RuntimeException("Senha inválida.");
        }
        return loadPayload(infoLogin);
    }

    protected String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }

    private String loadPayload(final Users infoLogin) {
        Object infoUser = usersService.loadInfoUser(infoLogin);

        return generateToken(infoLogin.getEmail(), infoUser);
    }

    private String generateToken(String email, Object obj) {
        return jwtHelper.generateToken(email, obj);
    }
}
