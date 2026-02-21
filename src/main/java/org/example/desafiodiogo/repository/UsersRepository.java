package org.example.desafiodiogo.repository;

import org.example.desafiodiogo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT * FROM users where email = :email", nativeQuery = true)
    Optional<Users> findUsersByEmail(@Param("email") String email);

    @Query(value = "SELECT \n" +
            "    u.tipo,\n" +
            "    m.nome AS materia,\n" +
            "    s.nome AS serie\n" +
            "FROM users u\n" +
            "LEFT JOIN alunos_series als \n" +
            "    ON als.user_id = u.id\n" +
            "LEFT JOIN series_materias sm \n" +
            "    ON sm.serie_id = als.serie_id\n" +
            "LEFT JOIN professores_materias_series pms\n" +
            "    ON pms.user_id = u.id\n" +
            "LEFT JOIN materias m\n" +
            "    ON m.id = COALESCE(sm.materia_id, pms.materia_id)\n" +
            "LEFT JOIN series s\n" +
            "    ON s.id = COALESCE(als.serie_id, pms.serie_id)\n" +
            "WHERE u.email = :email\n" +
            "AND u.tipo = :tipo;\n", nativeQuery = true)
    Optional<Map<String, Object>> loadInfoUser(String email, String tipo);

}
