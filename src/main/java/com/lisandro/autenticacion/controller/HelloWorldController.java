package com.lisandro.autenticacion.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@PreAuthorize("denyAll()")
public class HelloWorldController {
    @GetMapping("/holaseg")
    @PreAuthorize("hasAuthority('READ')")
    public String getMethodName() {
        return "HolaMundo";
    }


    @GetMapping("/holanoseg")
    @PreAuthorize("permitAll()")
    public String getAuth() {
        return "No requiere auth";
    }
    
    
}
