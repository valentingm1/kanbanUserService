package com.example.user_service.dto.entrada;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignInEntradaDTO {
    @NotBlank
    @NotEmpty
    @NotNull
    private String email;

    @NotBlank
    @NotEmpty
    @NotNull
    @Size(min = 6, message = "Por lo menos 6 caracteres")
    private String Password;


    public SignInEntradaDTO(String email, String password) {
        this.email = email;
        Password = password;
    }

    public SignInEntradaDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
