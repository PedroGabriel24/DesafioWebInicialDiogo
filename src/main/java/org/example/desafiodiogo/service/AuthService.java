package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.auth.AuthRequestParams;

public interface AuthService {

    String loginUser(final AuthRequestParams params);

}
