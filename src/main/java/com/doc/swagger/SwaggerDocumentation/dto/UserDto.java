package com.doc.swagger.SwaggerDocumentation.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private Long id;

    private String userName;

    private String password;

    private String email;

    private Boolean admin;

    private String role;

    public UserDto() {
    }

    public UserDto(String userName, String password, String email, Boolean admin, String role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.admin = admin;
        this.role = role;
    }

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(Long id, String userName, String password, String email, Boolean admin, String role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.admin = admin;
        this.role = role;
    }

    public UserDto(Long id, String userName, String email, Boolean admin, String role) {
        this.id = id;
        this.userName = userName;
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
