package com.example.user_service.dto.entrada;


import com.example.user_service.entity.Role;
import jakarta.validation.constraints.*;

public class UserEntradaDTO {
    @NotBlank
    @NotEmpty
    @NotNull
    private String Name;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 6, message = "Por lo menos 6 caracteres")
    private String Password;

    @NotBlank
    @NotEmpty
    @NotNull
    private String email;


    public UserEntradaDTO(String name, String password, String email) {
        Name = name;
        Password = password;
        this.email = email;
    }

    public UserEntradaDTO() {
    }

    public @NotBlank @NotEmpty @NotNull String getName() {
        return Name;
    }

    public void setName(@NotBlank @NotEmpty @NotNull String name) {
        Name = name;
    }

    public @NotBlank @NotEmpty @NotNull @Size(min = 6, message = "Por lo menos 6 caracteres") String getPassword() {
        return Password;
    }

    public void setPassword(@NotBlank @NotEmpty @NotNull @Size(min = 6, message = "Por lo menos 6 caracteres") String password) {
        Password = password;
    }

    public @NotBlank @NotEmpty @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @NotEmpty @NotNull String email) {
        this.email = email;
    }
}
