package com.doc.swagger.SwaggerDocumentation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app-users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private String email;

    private Boolean admin;

    private String role;

    public User() {
    }

    public User(Long id, String userName, String password, String email, Boolean admin, String role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.admin = admin;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", admin=" + admin +
                ", role='" + role + '\'' +
                '}';
    }
}
