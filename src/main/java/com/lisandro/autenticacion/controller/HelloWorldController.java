package com.lisandro.autenticacion.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class HelloWorldController {
    @GetMapping("/holaseg")

    public String getMethodName() {
        return "HolaMundo";
    }

    @GetMapping("/holanoseg")
    public String getAuth() {
        return "No requiere auth";
    }
    
    
}
