package com.example.user_service.dto.salida;

public class RoleSalidaDTO {
    private String name;
    private Long id;

    public RoleSalidaDTO(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public RoleSalidaDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
