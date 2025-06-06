package com.example.user_service.dto.salida;

public class SignInSalidaDTO {
    private String token;
    private String role;
    private String email;
    private String name;

    public SignInSalidaDTO(String token, String role, String email, String name) {
        this.token = token;
        this.role = role;
        this.email = email;
        this.name = name;
    }

    public String getToken() { return token; }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}