package com.example.user_service.service;

import com.example.user_service.dto.entrada.RoleEntradaDTO;
import com.example.user_service.dto.salida.RoleSalidaDTO;

public interface iRoleService {
    RoleSalidaDTO createRole(RoleEntradaDTO roleEntradaDTO);
}
