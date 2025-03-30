package com.example.user_service.controller;


import com.example.user_service.dto.entrada.RoleEntradaDTO;
import com.example.user_service.dto.salida.RoleSalidaDTO;
import com.example.user_service.service.iRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@Tag(name = "Role", description = "All /roles/ related endpoints")
public class RoleController {
    private final iRoleService RoleService;

    @Autowired
    public RoleController(iRoleService roleService) {
        this.RoleService = roleService;
    }

    @PostMapping("/add")
    @Operation(summary = "Create a new role", description = "Creates a new role and returns the created entity.")
    public ResponseEntity<RoleSalidaDTO> createRole(@Validated @RequestBody RoleEntradaDTO roleEntradaDTO) {
        return new ResponseEntity<>(RoleService.createRole(roleEntradaDTO), HttpStatus.CREATED);
    }
}

