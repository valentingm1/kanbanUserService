package com.example.user_service.mapper;

import com.example.user_service.dto.entrada.UserEntradaDTO;
import com.example.user_service.dto.salida.UserSalidaDTO;
import com.example.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.name", target = "roleName")
    UserSalidaDTO EntitytoSalidaDTO(User user);


    // Mapea la entrada de datos de usuario a la entidad usuario
    @Mapping(target = "id", ignore = true) // El ID se genera automáticamente
    User toEntity(UserEntradaDTO UserEntradaDTO);
}
