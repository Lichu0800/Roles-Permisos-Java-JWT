package com.lisandro.autenticacion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.lisandro.autenticacion.model.UserSec;
import com.lisandro.autenticacion.repository.IUserRepository;
import com.lisandro.autenticacion.utils.JwtUtils;
import com.lisandro.autenticacion.dto.AuthResponseDTO;
import com.lisandro.autenticacion.dto.AuthLoginRequestDTO;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
        @Autowired
        private IUserRepository userRepo;

        @Autowired
        private JwtUtils jwtUtils;

        @Autowired
        private PasswordEncoder passwordEncoder;

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

        public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
                String username = authLoginRequest.username();
                String password = authLoginRequest.password();
                Authentication authentication = this.authenticate(username, password);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String accessToken = jwtUtils.createToken(authentication);
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "Login successfull", accessToken, true);
                return authResponseDTO;

        }

        public Authentication authenticate(String username, String password) {
                UserDetails userDetails = this.loadUserByUsername(username);
                if (userDetails == null) {
                        throw new BadCredentialsException("Invalid username or password");
                }
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                        throw new BadCredentialsException("Invalid username or password");
                }
                return new UsernamePasswordAuthenticationToken(password, userDetails.getPassword(),
                                userDetails.getAuthorities());
        }

}
