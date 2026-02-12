package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.config.security.JwtProvider;
import org.example.desafiodiogo.dto.auth.AuthRequestParams;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.service.AuthService;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtHelper;
    private final UsersService usersService;

    public String loginUser(final AuthRequestParams params) {
        Users infoLogin = usersService.loginUser(params.getEmail());
        if (!passwordEncoder.matches(params.getSenha(), infoLogin.getPassword())) {
            throw new RuntimeException("Senha inválida.");
        }
        return loadPayload(infoLogin);
    }

    protected static String encodePassword(final String password) {
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
