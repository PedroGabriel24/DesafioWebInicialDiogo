package org.example.desafiodiogo.api.impl;

import lombok.RequiredArgsConstructor;
import org.example.desafiodiogo.api.AuthApi;
import org.example.desafiodiogo.dto.auth.AuthRequestParams;
import org.example.desafiodiogo.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthApi {

    private final AuthService authService;

    @Override
    public String loginUser(AuthRequestParams authRequestParams) {
        return authService.loginUser(authRequestParams);
    }
}
