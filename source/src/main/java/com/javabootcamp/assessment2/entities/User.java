package com.javabootcamp.assessment2.entities;

import com.javabootcamp.assessment2.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
public class User {
    public User() {}

    public User(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String username;
    private String password;
    private UserRole userRole;
}
