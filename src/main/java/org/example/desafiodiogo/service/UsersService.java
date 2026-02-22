package org.example.desafiodiogo.service;

import org.example.desafiodiogo.dto.auth.ProfileJWTToken;
import org.example.desafiodiogo.dto.users.UsersRequest;
import org.example.desafiodiogo.model.ProfileEnum;
import org.example.desafiodiogo.model.Users;

import java.util.List;
import java.util.Map;

public interface UsersService {

    Users findUsersByEmail(String email);

    Users findUsersById(Long id);

    List<Map<String, Object>> loadInfoUser(String email, ProfileEnum tipo);

}
