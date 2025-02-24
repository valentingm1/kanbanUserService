package com.example.user_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Role")
public class Role {

    @Column(unique = true)
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Role(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public Role() {
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
