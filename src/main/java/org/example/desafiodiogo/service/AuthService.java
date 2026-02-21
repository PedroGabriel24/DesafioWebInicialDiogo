package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.auth.AuthRequestParams;
import org.example.desafiodiogo.model.Users;

public interface AuthService {

    String loginUser(final AuthRequestParams params);

    Users getCurrentUser();

}
