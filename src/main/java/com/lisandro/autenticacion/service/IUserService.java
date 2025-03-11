package com.lisandro.autenticacion.service;

import java.util.List;
import java.util.Optional;

import com.lisandro.autenticacion.model.UserSec;

public interface IUserService {

    public List<UserSec> findAll();

    public Optional<UserSec> findById(Long id);

    public UserSec save(UserSec userSec);

    public void deleteById(Long id);

    public void update(UserSec userSec);

    String encriptPassword(String password);

}
