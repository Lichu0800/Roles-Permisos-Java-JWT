package com.lisandro.autenticacion.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/holanoseg").permitAll() // AUTORIZAR TODAS LAS REQUEST QUE COMIENCEN CON ESE CODIGO
                .anyRequest().authenticated() // EL RESTO DE URLS NECESITAN AUTH 
            )
            .formLogin(formLogin -> formLogin.permitAll()) // AL FORMULARIO PUEDE INGRESAR CUALQUIERA
            .httpBasic(Customizer.withDefaults());
        return http.build(); // "CREAR" LA HTTP Y BUILDERLA
    }
        

}
