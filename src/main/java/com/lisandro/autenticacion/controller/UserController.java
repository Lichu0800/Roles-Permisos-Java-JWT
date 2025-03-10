package com.lisandro.autenticacion.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.autenticacion.model.Role;
import com.lisandro.autenticacion.model.UserSec;
import com.lisandro.service.IRoleService;
import com.lisandro.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        UserSec user = userService.findById(id).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec user) {
        Set<Role> validList = new HashSet<>();
        Role readRol;

        for (Role rol : user.getRolesList()) {
            readRol = roleService.findById(rol.getId()).orElse(null);
            if (readRol != null) {
                validList.add(readRol);
            }
        }
        user.setRolesList(validList);
        userService.save(user);
        return ResponseEntity.ok(user);

    }

}
