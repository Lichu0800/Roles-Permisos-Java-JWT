package com.lisandro.autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lisandro.autenticacion.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
}
