package com.example.user_service.service;

import com.example.user_service.dto.entrada.UserEntradaDTO;
import com.example.user_service.dto.salida.UserSalidaDTO;

import java.util.List;

public interface iUserService {
    UserSalidaDTO createUser(UserEntradaDTO userDTO);

    List<UserSalidaDTO> getAllUsers();

    UserSalidaDTO getOneUser(Long id);

    void deleteOneUser(Long id);
}
