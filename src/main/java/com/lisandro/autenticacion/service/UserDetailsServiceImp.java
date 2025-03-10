package com.lisandro.autenticacion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.lisandro.autenticacion.model.UserSec;
import com.lisandro.autenticacion.repository.IUserRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
        @Autowired
        private IUserRepository userRepo;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserSec userSec = userRepo.findUserEntityByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "El usuario" + username + " no fue encontrado"));

                // CREAMOS UNA LISTA PARA LOS PERMISOS
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

                // Traer roles y convertiros en SimpleGrantedAuthority
                userSec.getRolesList()
                                .forEach(role -> authorityList
                                                .add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

                // Traer permisos y convertirlos a SimpleGrantedAuthority
                userSec.getRolesList().stream()
                                .flatMap(role -> role.getPermissionsList().stream())
                                .forEach(permission -> authorityList
                                                .add(new SimpleGrantedAuthority(permission.getPermissionName())));

                return new User(
                                userSec.getUsername(),
                                userSec.getPassword(),
                                userSec.isEnabled(),
                                userSec.isAccountNotExpired(),
                                userSec.isCredentialNotExpired(),
                                userSec.isAccountNotLocked(),
                                authorityList);
        }

}
