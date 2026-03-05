package org.example.desafiodiogo.service;

import org.example.desafiodiogo.model.enums.ProfileEnum;
import org.example.desafiodiogo.model.Users;

import java.util.List;
import java.util.Map;

public interface UsersService {

    Users findUsersByEmail(String email);

    Users findUsersById(Long id);

    List<Map<String, Object>> loadInfoUser(String email, ProfileEnum tipo);

}
