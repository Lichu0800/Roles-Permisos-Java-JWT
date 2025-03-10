package com.lisandro.autenticacion.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita una proteccion INVESTIGAR
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Politica
                .authorizeHttpRequests(authorize -> authorize
                        // Deja "publico" el endpoint para el metodo GET
                        .requestMatchers(HttpMethod.GET, "/holanoseg").permitAll()
                        // Para este endpoint con el metodo GET pide permisos READ
                        .requestMatchers(HttpMethod.GET, "/holaseg").hasAnyAuthority("READ") //
                        .anyRequest().denyAll() // Cualquier otra solicitud la deniega
                )
                .formLogin(formLogin -> formLogin.permitAll()) // AL FORMULARIO PUEDE INGRESAR CUALQUIERA
                .httpBasic(Customizer.withDefaults());
        return http.build(); // "CREAR" LA HTTP Y BUILDERLA
    }

    // AUTH MANAGER
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // AUTH PROVIDERS
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        // Crea una instancia de authProvider para utilizarlo como objeto
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());//
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    // Manejo del metodo con el cual vamos a encriptar la contraseña para este
    // ejemplo queda default
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
