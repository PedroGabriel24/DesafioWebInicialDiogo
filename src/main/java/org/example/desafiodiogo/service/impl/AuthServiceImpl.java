package org.example.desafiodiogo.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.config.security.JwtProvider;
import org.example.desafiodiogo.dto.auth.AuthRequestParams;
import org.example.desafiodiogo.model.Users;
import org.example.desafiodiogo.service.AuthService;
import org.example.desafiodiogo.service.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtHelper;
    private final UsersService usersService;

    public String loginUser(final AuthRequestParams params) {
        Users infoLogin = usersService.findUsersByEmail(params.getEmail());
        if (!passwordEncoder.matches(params.getSenha(), infoLogin.getPassword())) {
            throw new RuntimeException("Senha inválida.");
        }
        return loadPayload(infoLogin);
    }

    public Users getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (!(principal instanceof Users)) {
            String email = auth.getName();
            return usersService.findUsersByEmail(email);
        } else {
            return (Users) principal;
        }
    }

    protected static String encodePassword(final String password) {
        return passwordEncoder.encode(password);
    }

    private String loadPayload(final Users infoLogin) {
        Object infoUser = usersService.loadInfoUser(infoLogin.getEmail(), infoLogin.getTipo());

        return generateToken(infoLogin.getEmail(), infoUser);
    }

    private String generateToken(String email, Object obj) {
        return jwtHelper.generateToken(email, obj);
    }
}
