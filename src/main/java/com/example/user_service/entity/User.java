package com.example.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "Users")
public class User {
    private String name;
    private String Password;
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name", referencedColumnName = "name") // Usa el nombre del rol como clave foránea
    private Role role;

    public User(String name, String password, String email, Long id, Role role) {
        this.name = name;
        Password = password;
        this.email = email;
        this.id = id;
        this.role = role;
    }

    public User() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoleString(){
        return role.getName();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", Password='" + Password + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", role=" + role.getName() +
                '}';
    }
}
