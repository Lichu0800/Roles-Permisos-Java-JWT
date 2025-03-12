package com.lisandro.autenticacion.controller;

import com.lisandro.autenticacion.model.Permission;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import com.lisandro.autenticacion.service.IPermissionService;
import com.lisandro.autenticacion.service.IRoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permiService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping

    public ResponseEntity<Role> createRol(@RequestBody Role role) {
        Set<Permission> permiList = new HashSet<>(); // GUARDO LOS PERMISOS QUE MATCHEEN CON LA DB
        Permission readPermission; // VARIABLE AUXILIAR PARA GUARDAR EL PERMISO LEIDO
        // RECORRO LA LISTA DE PERMISOS
        for (Permission per : role.getPermissionsList()) { // RECORRE LA LISTA DE ROLES QUE SE PASA POR LA REQUEST
            readPermission = permiService.findById(per.getId()).orElse(null);// BUSCA EL PERMISO POR ID SI NO LO
                                                                             // ENCUENTRA DEVUELVE NULL
            if (readPermission != null) { // SI NO ES NULL AÑADE A LA LISTA DE PERMISOS VALIDOS
                permiList.add(readPermission);
            }
        }
        role.setPermissionsList(permiList); // CON EL SETTER, METE EN EL ROL LA LISTA DE ROLES VALIDADOS
        Role newRole = roleService.save(role); // GUARDA EL ROL
        return ResponseEntity.ok(newRole);// LO DEVUELVE EN UN RESPONSE

    }

}
