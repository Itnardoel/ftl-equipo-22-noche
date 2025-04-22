package com.footalentgroup.models.entities;

import com.footalentgroup.models.enums.RoleList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "users")
@Data @Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleList role;

    public User() {}

    public User(String email, String name, String lastName, String password, RoleList role) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {
        return name + " " + lastName;
    }
}
