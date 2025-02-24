package com.example.user_service.controller;


import com.example.user_service.dto.entrada.RoleEntradaDTO;
import com.example.user_service.dto.salida.RoleSalidaDTO;
import com.example.user_service.service.iRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/roles")
public class RoleController {
    private iRoleService RoleService;

    @Autowired
    public RoleController(iRoleService roleService) {
        RoleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<RoleSalidaDTO> createRole(@Validated @RequestBody RoleEntradaDTO roleEntradaDTO){
        return new ResponseEntity<>(RoleService.createRole(roleEntradaDTO), HttpStatus.CREATED);
    }
}
