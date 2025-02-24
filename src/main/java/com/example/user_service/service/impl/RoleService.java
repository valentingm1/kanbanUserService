package com.example.user_service.service.impl;

import com.example.user_service.dto.entrada.RoleEntradaDTO;
import com.example.user_service.dto.salida.RoleSalidaDTO;
import com.example.user_service.entity.Role;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.repository.RoleRepository;
import com.example.user_service.service.iRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements iRoleService {
    private static final Logger log = LoggerFactory.getLogger(RoleService.class);
    private final RoleRepository roleRepository;
    @Autowired
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleSalidaDTO createRole(RoleEntradaDTO roleEntradaDTO){
        log.info("Creating {} as a Role",roleEntradaDTO);
        Role role = roleMapper.EntradaDTOToEntity(roleEntradaDTO);

        Role savedRole = roleRepository.save(role);

        RoleSalidaDTO roleSalidaDTO = roleMapper.RoleEntityToSalidaDTO(savedRole);

        return roleSalidaDTO;
    }
}
