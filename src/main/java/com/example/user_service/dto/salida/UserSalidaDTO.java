package com.example.user_service.dto.salida;

import com.example.user_service.entity.Role;

import jakarta.validation.constraints.Email;

public class UserSalidaDTO {

    private String name;
    private String email;
    private Long id;
    private String roleName;


    public UserSalidaDTO(String name, String email, Long id, String roleName) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.roleName = roleName;
    }

    public UserSalidaDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "UserSalidaDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}

