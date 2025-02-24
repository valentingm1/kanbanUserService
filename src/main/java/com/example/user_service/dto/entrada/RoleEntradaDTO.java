package com.example.user_service.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RoleEntradaDTO {
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;

    public RoleEntradaDTO(String name) {
        this.name = name;
    }

    public RoleEntradaDTO() {
    }

    public @NotNull @NotBlank @NotEmpty String getName() {
        return name;
    }

    public void setName(@NotNull @NotBlank @NotEmpty String name) {
        this.name = name;
    }
}
