package com.example.user_service.mapper;



import com.example.user_service.dto.entrada.RoleEntradaDTO;
import com.example.user_service.dto.salida.RoleSalidaDTO;
import com.example.user_service.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    RoleSalidaDTO RoleEntityToSalidaDTO(Role role);

    Role EntradaDTOToEntity(RoleEntradaDTO roleEntradaDTO);
}
