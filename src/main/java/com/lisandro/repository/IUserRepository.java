package com.lisandro.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lisandro.autenticacion.model.UserSec;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {

    // Crea la sentencia en base al nombre en inglés del método
    // Tmb se puede hacer mediante Query pero en este caso no es necesario
    Optional<User> findUserEntityByUsername(String username);

}
