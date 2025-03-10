package com.lisandro.autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lisandro.autenticacion.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

}
